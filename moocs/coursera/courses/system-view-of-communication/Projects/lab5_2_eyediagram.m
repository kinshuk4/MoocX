tx_bs=rand(1,1280)>0.5;      % generate a random bit sequence

% Modify the code below so that instead of generating one eye diagram, it
% generates four eye diagrams with SPBs 20, 12, 5 and 1.
% Each eye diagram should be plotted in a separate subwindow as follows
%     SPB=20    SPB=12
%     SPB=5     SPB=1

p = 1
for SPB = [20 12 5 1]

    % transmit/receive bit sequence and compute BER
    tx_wave = format_bitseq(tx_bs,SPB);  % create waveform following protocol
    rx_wave=txrx(tx_wave);         % simulate channel
    start_ind=find_start(rx_wave);       % find start bit
    % decode received waveform without equalization
    rx_bs = waveform2bitseq(rx_wave,SPB,0);
    BER = compute_BER(tx_bs,rx_bs);      % compute the BER

    disp(['The BER for SPB ' num2str(SPB) ' is ' num2str(BER) '.'])

    % plot eye diagram in a subplot of figure window
    subplot(2,2,p)
    eye_diagram(rx_wave,start_ind,SPB); % plot eye diagram
    title(['Eye Diagram, SPB = ' num2str(SPB)]);
    xlabel('Sample index')
    ylabel('Amplitude');
    grid on;
    p = p + 1;
    
end
