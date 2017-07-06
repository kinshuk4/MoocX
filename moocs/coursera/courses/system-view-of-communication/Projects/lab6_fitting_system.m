% The channel_type variable changes the properties of the channel.
% Here we assume a channel that cannot exactly be modelled with a step
% response that contains a single exponential.
channel_type = 'doubleexp';
distance = 10;

tx_wave = [zeros(1,150) ones(1,250)];   % define step-like waveform
% transmit waveform through channel
rx_wave = txrx(tx_wave,distance,channel_type); 

% fit_rcv(rx_wave,c,d,k,a) fits rx_wave by a function of the form
%     y(n) = c + k*(1-a^(n-d)) for n >= d and 0 otherwise. 
% modify the values below to find the correct fit
c = 0.23;
d = 150;
k = 0.60;
a = 0.916;        
% do not modify code below
mse = fit_rcv(rx_wave,c,d,k,a);  % fit channel output to model
display(['MSE = ' num2str(mse)]); % print out MSE of fit        
        
      