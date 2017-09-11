#!/bin/bash
#Script to get all repositories under a user from bitbucket
#Usage: getAllRepos.sh [username]

curl -u ${1}  https://api.bitbucket.org/1.0/users/${1} > repoinfo
repoType="git"
for repo_name in `cat repoinfo | jq '.repositories[]' | jq .name | sed 's/\"//g'`
do
	$repoType clone https://${1}@bitbucket.org/${1}/$repo_name
done

# Note: mercurial users can replace repoType to "hg"
# Also, I am using "https" but we can also ssh if you don't need to import the projec
# $repoType clone ssh://$repoType@bitbucket.org/${1}/$repo_name