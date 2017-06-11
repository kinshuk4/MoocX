# Git

## Pushing

### Push a specific commit to a remote branch

If the remote branch already exists:
```
git push origin <SHA1>:<branch>
```

If not:
```
git push origin <SHA1>:refs/heads/<branch>
```

### Delete a remote branch

```
git push origin :<branch>
```

For [Git v1.7.0](https://github.com/gitster/git/blob/master/Documentation/RelNotes/1.7.0.txt) or later, a syntatic sugar is added:
```
git push origin --delete <branch>
```

## Merging

### Find common ancestor of two commits
```
git merge-base <commit> <commit>
```

## Misc

### Find the root directory

```
git rev-parse --show-toplevel
```

### Find the name of the current branch

```
git symbolic-ref --short HEAD
```

or

```
git rev-parse --abbrev-ref HEAD
```

### Check if a local branch exists
```
git show-ref refs/heads/<branch> --quiet --verify
```

### Check if a remote branch exists
```
git ls-remote --exit-code --heads origin refs/heads/<branch> &>/dev/null
```

### Find the root commit
```
git rev-list --max-parents=0 HEAD
```

### Remove the first commit
```
git update-ref -d HEAD
```
