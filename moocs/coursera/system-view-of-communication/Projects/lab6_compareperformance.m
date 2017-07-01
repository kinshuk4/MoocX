% The channel_type variable changes the properties of the channel.
% Here we assume a channel that cannot exactly be modelled with a step
% response that contains a single exponential.
channel_type = 'doubleexp';
distance = 10;

tx_bs=rand(1,1280)>0.5;     % generate a random bit sequence

SPBlist = 1:20;             % list of bit times to test
num_SPB = length(SPBlist);  % number of bit times to test
BER = zeros(1,num_SPB);     % initialize bit error rate array
BER_eq = zeros(1,num_SPB);     % initialize bit error rate array

for i = 1:num_SPB,
    SPB = SPBlist(i);
    tx_wave = format_bitseq(tx_bs,SPB);    % create waveform
    rx_wave = txrx(tx_wave,distance,channel_type);         % simulate channel 
    start_ind = find_start(rx_wave); % find start bit

    % get bits from unequalized waveform
    threshold = get_threshold(rx_wave);
    rx_bs = get_bits(rx_wave,SPB,threshold,start_ind,1280,2*SPB-1);
    BER(i) = compute_BER(tx_bs,rx_bs);  % compute the BER

    % Replace the line below so that BER_eq stores the BER for the equalized waveform
    % Get equalized waveform
    a = 0.916;
    eq_wave = equalizer(rx_wave,a);
    rx_bs_eq = get_bits(eq_wave, SPB, threshold, start_ind,1280,2*SPB-1); 
    BER_eq(i) = compute_BER(tx_bs, rx_bs_eq);
    % Donot modify code below  
end

% code to plot and label results
plot(SPBlist,BER_eq,'r','LineWidth',2);
hold on;
plot(SPBlist,BER,'b','LineWidth',2);
title('Bit Error Rate vs SPB');
xlabel('Bit Time (samples)')
ylabel('BER');
axis([0 20 0 0.5]);
grid;
legend('with equalizer','without equalizer');
      