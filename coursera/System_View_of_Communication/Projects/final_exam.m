% bs_raw = rand(1,60)>0.5;   % generate random 60 bit sequence
% 
% % place your code below that 
% % encodes bitstream bs_raw and store the encoded bit in bs_enc  
% % bs_enc = [];
% % for i = 1:3:60
% %     next_seq = bs_raw(i:(i+2));
% %     p1 = 0;
% %     p2 = 0;
% %     p3 = 0;
% %     if next_seq(1) + next_seq(2) == 1
% %         p1 =1;
% %     end
% %     if next_seq(2) + next_seq(3) == 1
% %         p2 =1;
% %     end
% %     if next_seq(3) + next_seq(1) == 1
% %         p3 =1;
% %     end
% %     
% %     bs_enc = [bs_enc next_seq p1 p2 p3];
% % end

% Generate a random 10 bit sequence and a random SPB
% Do not change the values of bs and SPB in your code
%[bs,SPB] = bit_seq_gen_final;  

SPB = 20;
bs = [0 0 0 1 1 1 0 1 0 0];

n = 0:(length(bs)*SPB-1);      % sample index     
y = zeros(1,length(bs)*SPB);   % Vector to store the channel response 

% Modify the code below to predict the channel response 
y = y + step_response_final(n)-step_response_final(n-SPB);

disp(step_response_final(n))
% Do not modify the code below
compare(y,bs,SPB);  % Compare the predicted and actual responses

