
% The channel_type variable changes the properties of the channel.
% Here we assume a channel that cannot exactly be modelled with a step
% response that contains a single exponential.
channel_type = 'doubleexp';
distance = 10;

tx_bs = rand(1,1280)>0.5;      % generate a random bit sequence
SPB = 20;                      % bit time in samples

tx_wave = format_bitseq(tx_bs,SPB);    % create waveform
rx_wave = txrx(tx_wave,distance,channel_type); % simulate channel
start_ind = find_start(rx_wave); % find start bit

% Place your code below that applies an equalizer to the rx_wave and 
% stores the output as eq_wave. Take into account only the effects of a, by
% assuming that k = 1 and c = 0.  To start, you should assume that
% rx_wave(0) = rx_wave(1)
        
eq_wave = zeros(size(rx_wave)); % initialize equalizer output        
a = 0.916;         

for indx = 1:length(rx_wave)
    if indx == 1
        eq_wave(1,indx) = rx_wave(1);
    else
        eq_wave(1,indx) = (1/(1-a))*rx_wave(1,indx) -(a/(1-a))*rx_wave(1,indx-1);
    end
end
% plot eye diagrams - do not modify code below
% plot eye diagram without equalization
subplot(2,1,1);
eye_diagram(rx_wave,start_ind,SPB);
title(['Eye Diagram without equalization, SPB = ' num2str(SPB) ]);
xlabel('Sample index')
ylabel('Amplitude');
grid on;
% plot eye diagram with equalization
subplot(2,1,2);
eye_diagram(eq_wave,start_ind,SPB);
title(['Eye Diagram with equalization, SPB = ' num2str(SPB) ]);
xlabel('Sample index')
ylabel('Amplitude');
grid on;       
      