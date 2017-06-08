# Vim

## Movements
- `h` ←
- `l` →
- `k` ↑
- `j` ↓
- `0` Go to the first character of the line
- `^` Go to the first non-blank charater of the line
- `$` Go to the end of the line
- `gg` Go to the first line
- `G` Go to the last line

## Buffers
- `:ls` List buffers
- `:e` Reload current bufffer
- `:bd!|e [file]` Open `file` in current buffer
- `:N,Mbd` Delete buffers from `N` to `M`

## Windows
- `:q` Quit the current window
- `:q!` Quit the current window, discard any changes
- `:clo` Close the current window, unless it is the last one
- `:on` Make the current window the only one
- `Ctrl-W s` Create a horizontal split
- `Ctrl-W v` Create a vertical split
- `Ctrl-W h` Go to left window
- `Ctrl-W l` Go to right window
- `Ctrl-W k` Go to up window
- `Ctrl-W j` Go to down window

## Commands
To search through command history, enter the prefix of your previous command and push \<Up\>.

To configure command history size
```
:set history=1000
```

## Folds
- `zo` Open one fold
- `zc` Close one fold
- `zr` Reduce folding
- `zm` Fold more
- `zR` Open all folds
- `zM` Close all folds

## Diff mode
- `[c` Jump to the previous conflict
- `]c` Jump to the next conflict
- `:diffg [bufspec]` Copy change from `[bufspec]` the current buffer
- `:diffp [bufspec]` Copy change from the current buffer to `[bufspec]`
- `:'<,'>diffg` `:'<,'>diffp` Copy change in the selected region
