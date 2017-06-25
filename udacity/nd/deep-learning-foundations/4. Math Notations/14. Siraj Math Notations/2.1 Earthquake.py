from datetime import datetime
import numpy as np
import copy


class Dataset:
    @staticmethod
    def load_from_file(filename):
        '''
        Load and return data from file
        :param filename: path of the database.csv file
        :return: (date, latitude, longitude, magnitude) (np.array)
        '''
        date, latitude, longitude, magnitude = [], [], [], []

        with open(filename, "r") as f:
            f.readline()  # Skip first line

            for line in f:
                elements = line.split(',')
                try:
                    date.append(datetime.strptime(f"{elements[0]} {elements[1]}", "%m/%d/%Y %H:%M:%S"))
                    latitude.append(float(elements[2]))
                    longitude.append(float(elements[3]))
                    magnitude.append(elements[8])
                except ValueError:
                    pass

        return np.array(date), np.float32(latitude), np.float32(longitude), np.float32(magnitude)

    @staticmethod
    def normalize_date(array):
        '''
        Normalize datetime array
        :param array: array to normalize
        :return: normalized array (np.array)
        '''
        min_data = min(array)
        max_data = max(array)
        delta = max_data - min_data

        return np.float32([(d - min_data).total_seconds() / delta.total_seconds() for d in array])

    @staticmethod
    def normalize_cord(latitude, longitude):
        '''
        Normalize GPS cord array, assuming the earth is shpherical
        :param latitude: latitude array to normalize
        :param longitude: longitude array to normalize
        :return: normalized arrays (np.array)
        '''
        rad_lat = np.deg2rad(latitude)
        rad_lon = np.deg2rad(longitude)

        x = np.cos(rad_lat) * np.cos(rad_lon)
        y = np.cos(rad_lat) * np.sin(rad_lon)
        z = np.sin(rad_lat)

        return x, y, z

    @staticmethod
    def vectorize(date, latitude, longitude):
        '''
        Transform given array in a vectors to feed NN
        :param date: date array
        :param latitude: latitude array
        :param longitude: longitude array
        :return: np.array
        '''
        return np.concatenate(Dataset.normalize_cord(latitude, longitude) + (Dataset.normalize_date(date),))\
            .reshape((4, len(date)))\
            .swapaxes(0, 1)


class Math:
    @staticmethod
    def sigmoid(x, deriv=False):
        '''
        Sigmoïd function
        :param x: np.array
        :param deriv: derivate wanted ?
        :return:
        '''
        if deriv:
            return x * (1 - x)

        return 1 / (1 + np.exp(-x))

    @staticmethod
    def relu(x, deriv=False):
        '''
        Rectifier function
        :param x: np.array
        :param deriv: derivate wanted ?
        :return:
        '''
        if deriv:
            return np.ones_like(x) * (x > 0)

        return x * (x > 0)

    @staticmethod
    def new_parameters(x, x_min, x_max, radius):
        '''
        Generate new random parameters in the sphere of center and radius given
        :param x: center on the sphere
        :param x_min: minmium value returned
        :param x_max: maximum value returned
        :param radius: radius
        :return: new parameter
        '''
        alpha = 2 * np.random.random() - 1
        new_x = x + radius * alpha

        if new_x < x_min:
            return x_min
        elif new_x > x_max:
            return x_max

        return new_x


class Generator:
    @staticmethod
    def gen_random_batch(batch_size, X, Y):
        '''
        Generator for random batch
        :param batch_size: size or the returned batches
        :param X: X array
        :param Y: Y array
        :return: random batches of the given size
        '''
        while True:
            index = np.arange(X.shape[0])
            np.random.shuffle(index)

            s_X, s_Y = X[index], Y[index]
            for i in range(X.shape[0] // batch_size):
                yield (X[i * batch_size:(i + 1) * batch_size], Y[i * batch_size:(i + 1) * batch_size])

    @staticmethod
    def get_batch(batch_size, X, Y):
        '''
        Generator to split givens arrays in smaller batches
        :param batch_size: size or the returned batches
        :param X: X array
        :param Y: Y array
        :return: random batches of the given size
        '''
        if X.shape[0] % batch_size != 0:
            print("[/!\ Warning /!\] the full set will not be executed because of a poor choice of batch_size")

        for i in range(X.shape[0] // batch_size):
            yield X[i * batch_size:(i + 1) * batch_size], Y[i * batch_size:(i + 1) * batch_size]


if __name__ == "__main__":
    # Load and prepare data
    date, latitude, longitude, magnitude = Dataset.load_from_file("earthquake_data.csv")
    data_size = len(date)
    vectorsX, vectorsY = Dataset.vectorize(date, latitude, longitude), magnitude.reshape((data_size, 1))

    # Split vectors into train / eval sets
    eval_set_size = int(0.1 * data_size)
    index = np.arange(data_size)
    np.random.shuffle(index)
    trainX, trainY = vectorsX[index[eval_set_size:]], vectorsY[index[eval_set_size:]]
    evalX, evalY = vectorsX[index[:eval_set_size]], vectorsY[index[:eval_set_size]]

    # randomly initialize our weights with mean 0
    syn0_origin = 2 * np.random.random((trainX.shape[1], 32)) - 1
    syn1_origin = 2 * np.random.random((32, trainY.shape[1])) - 1

    # Placeholder for hyperparameters
    best_error = 9999
    best_learning_rate_log = -3
    best_momentum = 0.9
    best_batch_size = 64
    best_max_epochs_log = 4
    learning_rate_log = None
    momentum = None
    batch_size = None
    max_epochs_log = None

    for i in range(50):
        # Hyperparameters
        learning_rate_log = Math.new_parameters(best_learning_rate_log, -5, -1, 0.5)  # log range from 0.0001 to 0.1
        momentum = Math.new_parameters(best_momentum, 0.5, 0.95, 0.1)  # linear range from 0.5 to 0.9
        batch_size = np.int64(Math.new_parameters(best_batch_size, 10, 128, 10))  # linear range from 10 to 128
        max_epochs_log = Math.new_parameters(best_max_epochs_log, 3, 5, 0.5)  # log range from 1000 to 100000

        learning_rate = np.power(10, learning_rate_log)
        max_epochs = np.int64(np.power(10, max_epochs_log))

        # Display hyperparameters
        print(f"iteration: {i}")
        print(f"learning rate: {learning_rate}")
        print(f"momentum: {momentum}")
        print(f"batch size: {batch_size}")
        print(f"max epochs: {max_epochs}")

        # reset weight
        syn0 = copy.deepcopy(syn0_origin)
        syn1 = copy.deepcopy(syn1_origin)

        # initialize momentum
        momentum_syn0 = np.zeros_like(syn0)
        momentum_syn1 = np.zeros_like(syn1)

        # get batch generator
        batch_gen = Generator.gen_random_batch(batch_size, trainX, trainY)

        # Train model
        for j in range(max_epochs):
            # Get Batch
            batch = next(batch_gen)

            # feed forward
            l0 = batch[0]
            l1 = Math.sigmoid(np.dot(l0, syn0))
            l2 = Math.relu(np.dot(l1, syn1))

            # l2 error & delta
            l2_error = batch[1] - l2
            l2_delta = l2_error * Math.relu(l2, deriv=True)

            # l1 error & delta
            l1_error = l2_delta.dot(syn1.T)
            l1_delta = l1_error * Math.sigmoid(l1, deriv=True)

            # momentum
            momentum_syn1 = momentum * momentum_syn1 + l1.T.dot(l2_delta) * learning_rate
            momentum_syn0 = momentum * momentum_syn0 + l0.T.dot(l1_delta) * learning_rate

            # Apply momentum correction
            syn1 += momentum_syn1
            syn0 += momentum_syn0

        # Evaluate model
        current_error = 0
        for batch in Generator.get_batch(10, evalX, evalY):
            # feed forward
            l0 = batch[0]
            l1 = Math.sigmoid(np.dot(l0, syn0))
            l2 = Math.relu(np.dot(l1, syn1))

            # accumulate error
            current_error += np.sum(np.abs(batch[1] - l2))
        current_error /= eval_set_size

        print(f"error: {current_error}\n")

        if current_error < best_error:
            best_error = current_error
            best_learning_rate_log = learning_rate_log
            best_momentum = momentum
            best_batch_size = batch_size
            best_max_epochs_log = max_epochs_log

    print(f"best error: {best_error}")
    print(f"best learning rate log: {best_learning_rate_log}")
    print(f"best momentum: {best_momentum}")
    print(f"best batch size: {best_batch_size}")
    print(f"best max epochs log: {best_max_epochs_log}")
