tx_bs=rand(1,1280)>0.5;      % generate a random bit sequence
SPB=20;                      % bit time in samples

% transmitter %
tx_wave = format_bitseq(tx_bs,SPB);  % create waveform following protocol

% channel %
rx_wave = txrx(tx_wave);             % simulate channel

% receiver %
start_ind = find_start(rx_wave);     % find start bit

figure(1);
% Place your code below that

% Create Index
indx = [0:(2*SPB)];
%/(2*SPB);

plot_count = 0;
for i = (start_ind):(2*SPB):(length(rx_wave)-(2*SPB))
    temp = [];
    for j = i:(i+(2*SPB))
        new = rx_wave(j);
        temp = [temp new];
    end
    plot(indx,temp, '-k')
    hold on
    plot_count = plot_count + 1;
    if plot_count == 640
        break
    end
end;

disp(plot_count)

%   1. Creates the eye diagram of rx_wave, plotting "2*SPB+1" samples in each trace.  
%      Hint: use for loop.
%   2. To superimpose all the traces on the same plot. 
%      Hint: use command hold on.  

% code to label plot -- do not modify
title('Eye Diagram, SPB = 20');
xlabel('Sample index')
ylabel('Amplitude');
grid on;
      