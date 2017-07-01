% The channel_type variable changes the properties of the channel.
% Here we assume a channel that cannot exactly be modelled with a step
% response that contains a single exponential.
channel_type = 'doubleexp';
distance = 10;

tx_bs=rand(1,1280)>0.5;      % generate a random bit sequence
SPBList = [20 15 10 3];
        
for i=1:4,
    
    % Revise the following code    
    SPB = SPBList(i);
    a = 0.916;
    % Do not modify code below 
        
    tx_wave = format_bitseq(tx_bs,SPB);  % create waveform following protocol
    rx_wave = txrx(tx_wave,distance,channel_type); % simulate channel
    start_ind = find_start(rx_wave); % find start bit
    eq_wave = equalizer(rx_wave,a);      % equalize the received waveform

    % plot the eye diagrams
    figure(i)
    subplot(2,1,1);
    eye_diagram(rx_wave,start_ind,SPB);
    title(['Eye Diagram without equalization, SPB = ' num2str(SPB) ]);
    xlabel('Sample index');
    ylabel('Amplitude');
    grid on;
    subplot(2,1,2);
    eye_diagram(eq_wave,start_ind,SPB);
    title(['Eye Diagram with equalization, SPB = ' num2str(SPB) ]);
    xlabel('Sample index');
    ylabel('Amplitude');
    grid on;
end

% rearrange eye diagrams
for i=4:-1:1, figure(i), end;
      