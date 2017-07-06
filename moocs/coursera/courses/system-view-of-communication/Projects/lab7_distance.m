% The channel_type variable changes the properties of the channel.
% Here we assume a channel that is more noisy than previous channels.
channel_type = 'noisy';

SPB = 50;               % bit time in samples
distance = 8;           % transmission distance

tx_bs = [1:1280] > 640; % generate bit sequence of 640 0's and 640 1's
tx_wave = format_bitseq(tx_bs,SPB);   % create waveform following protocol

% transmit and receive over noisy channel
dists = [8 10 12 14];
for i = 1:4
    
    distance = dists(i)
    
    rx_wave = txrx(tx_wave,distance,channel_type);
    start_ind = find_start(tx_wave); % get start index
    [rx_min,rx_max,sigma] = get_noise_params; % get true noise parameters

    sample_ind = start_ind + 2*SPB-1 + SPB*[0:1279];  % choose subsampling points
    signal_samples = rx_wave(sample_ind);             % extract samples

    xhist = 0.1:0.01:0.7;   % centers of histogram bins
    % Do not modify code above this line

    subplot(4,1,i)
    % Place your code below that plots the empirical histogram of the 
    % received signal samples when IN=0
    nsamp = 640;
    the_zeros = signal_samples(1:640);
    y_vals = [];
    for indx = 1:(length(xhist))
        low_val = xhist(indx)-0.005;
        high_val = xhist(indx)+0.005;
        vec_l = ((the_zeros > low_val) & (the_zeros <= high_val));
        p_tmp = sum(vec_l)/nsamp;
        y_vals = [y_vals p_tmp];
    end
    bar(xhist,y_vals);

    % Do not modify next six lines
    hold on;                        % all plots on the same figure
    plot_ghist(xhist,rx_min,sigma); % plot histogram predicted by Gaussian
    title(['Histogram of received signals']);  % add title
    xlabel('Received signal');
    ylabel('Frequency (%)');
    hold on;


    %subplot(8,1,2+((2*i)-2))

    % Place your code below that 
    % 1. Plots the empirical histogram of the received signal samples when IN=1
    % 2. Plots the theoretically predicted histogram
    % Plot the empirical and theoretically predicted histogram versus xhist defined above.
    nsamp = 640;
    the_ones = signal_samples(641:end);
    y_vals = [];
    for indx = 1:(length(xhist))
        low_val = xhist(indx)-0.005;
        high_val = xhist(indx)+0.005;
        vec_l = ((the_ones > low_val) & (the_ones <= high_val));
        p_tmp = sum(vec_l)/nsamp;
        y_vals = [y_vals p_tmp];
    end
    bar(xhist,y_vals);
    hold on;
    plot_ghist(xhist,rx_max,sigma); % plot histogram predicted by Gaussian
    % do not modify code below this line
%     title(['Histogram of received signals when IN=1']);  % add title
%     xlabel('Received signal');
%     ylabel('Frequency (%)');
    hold off;
end
      