### Clone all your repos at once
https://stackoverflow.com/questions/19576742/how-to-clone-all-repos-at-once-from-github

USER=YOURUSERNAME; PAGE=1
curl "https://api.github.com/users/$USER/repos?page=$PAGE&per_page=100" |
  grep -e 'git_url*' |
  cut -d \" -f 4 |
  xargs -L1 git clone


### Change github url from GIT To HTTPS
git config --global url."https://".insteadOf git://
(https://stackoverflow.com/questions/15669091/bower-install-using-only-https)
