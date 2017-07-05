# Setting up with Anaconda

*These are notes on setting up packages and environments with Anaconda.*

About conda - [README.md](../../../../k2e/dev/tools/conda/README.md)

## AIND environment

To set AIND environment, please create your environment using the file [aind-environment-unix.yalm](https://github.com/robsonmatos/aind-notes/blob/master/md/aind-environment-unix.yalm).

Additionally, you may install Pygame. Bellow you find the instructions to install it on Mac OS X:

1. Install Homebrew;
2. brew install sdl sdl_image sdl_mixer sdl_ttf portmidi mercurial;
3. source activate aind;
4. pip install pygame.