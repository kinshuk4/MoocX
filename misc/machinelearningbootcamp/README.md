# Machine Learning Bootcamp Setup Instructions

## Install Python

Install Anaconda (Python 2.7) from:  [https://www.continuum.io/downloads](https://www.continuum.io/downloads)
This includes python 2.7.9 and the necessary libraries we will be using: "numpy", "matplotlib", "scipy" and "scikit-learn"

## Install Packages

Installing required packages using "pip"

Open your terminal and check whether you have the "pip" function installed by typing pip (and enter)
If you do not have pip installed, check the link: [https://pip.pypa.io/en/latest/installing/](https://pip.pypa.io/en/latest/installing/) (If installing via the terminal/command line, ensure you are in the directory where you have downloaded the file "get-pip" or if using chrome right-click on the link to download, save to desktop, and simply double click on the executable).

You may need to use `sudo pip install` (for OSX, *nix, etc) or run your command shell as Administrator (for Windows) to be able to perform the installation of the folllowing individual packages:

    (sudo) pip install Plotly
    (sudo) pip install keras

    (sudo) pip install --upgrade --no-deps git+git://github.com/Theano/Theano.git

or if you're using windows, follow the instructions at: [http://deeplearning.net/software/theano/install_windows.html#install-windows](http://deeplearning.net/software/theano/install_windows.html#install-windows)


If you already have any of the previously-mentioned libraries installed, you can update them to a newer version using the syntax:

    pip install <package> --upgrade

where `<package>` can be any of the libraries mentioned above.

## Install required packages with Conda

Once more in your terminal run:

    conda install opencv

If the opencv/cv2 library does not load and gives you an error while in step 8, you may want to try this newer version:

    conda install -c https://conda.binstar.org/menpo opencv


## Install git

Install git if you don't have it: [http://git-scm.com/](http://git-scm.com/)


##  Sign up for a GitHub

Sign up for a GitHub account or sign in if you have one: [github.com](https://github.com)


## Fork the code

Fork the CCA bootcamp repository at:

[https://github.com/cambridgecoding/machinelearningbootcamp.git](https://github.com/cambridgecoding/machinelearningbootcamp.git)

## Clone the code

Clone the code from your own repository.

## Download the data

Download the following file (may take some time) and place it in the day2 folder  

VGG16 weights (500MB) from [https://drive.google.com/file/d/0Bz7KyqmuGsilT0J5dmRCM0ROVHc/view](https://drive.google.com/file/d/0Bz7KyqmuGsilT0J5dmRCM0ROVHc/view)

## Finalise the setup

Open and run the "[load_libraries.ipynb](https://github.com/cambridgecoding/machinelearningbootcamp/blob/master/load_libraries.ipynb)" file, wait for the pre-fetching of the CIFAR10 dataset to be completed (it may take a while but a progress bar will show you the remaining time) and check whether the libraries have been successfully loaded.

To execute the notebook, in your terminal run:

    ipython notebook load_libraries.ipynb
