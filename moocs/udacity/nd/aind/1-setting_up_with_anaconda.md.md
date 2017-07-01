# Setting up with Anaconda

*These are notes on setting up packages and environments with Anaconda.*

## Anaconda

Anaconda manages packages and environments for use with Python. It provides a wealthy of packages often used in data science work as well as virtual environments to isolate different projects. Besides helping isolate your projects, environments help you share your work with others.

When you install Anaconda, you find the following contents:

- Conda (package and environment manager);
- Python;
- More than 150 scientific packages.

### Installation

Go to Anaconda's download page [here](https://www.continuum.io/downloads), select your operation system, and choose the Python's version you require.

## Managing packages

This section lists usual commands to manage packages.

| Managing packages | Command                             | Example                 |
| ----------------- | ----------------------------------- | ----------------------- |
| Install package   | *conda install *pkg_name*=*version* | conda install numpy=1.0 |
| Remove package    | conda remove *pkg_name*             | conda remove numpy      |
| Search package    | conda search *key-word*             | conda search bumpy      |
| List packages     | conda list                          | -                       |
| Update packages   | conda update --all                  | -                       |

*The command **conda install pkg_name=version** installs the package referred by *pkg_name* and the packages it depends on automatically.

## Managing environments

This section lists usual commands to manage environments.

| Managing environments              | Command                                  | Example                      |
| ---------------------------------- | ---------------------------------------- | ---------------------------- |
| Creating environments              | conda create -n *env_name* *list_of_pkgs* | conda create -n py3 python=3 |
| Creating env from an env.yalm file | conda env create -f *env_name*           | conda env create -f env.yalm |
| Saving environments                | conda env export > *file_name*           | conda env export > env.yalm  |
| Removing environments              | conda env remove -n *env_name*           | conda env remote -n py3      |
| Listing environments               | conda env list                           | -                            |
| Entering environments              | source activate *my_env*                 | source activate py3          |
| Leaving environments               | source deactivate                        | -                            |

## Best practices

1. Create separate environments for Python 2 and 3;
2. Usually, create environments for each project;
3. Include an environment file in every project so other contributors will easily reproduce your environment.

## AIND environment

To set AIND environment, please create your environment using the file [aind-environment-unix.yalm](https://github.com/robsonmatos/aind-notes/blob/master/md/aind-environment-unix.yalm).

Additionally, you may install Pygame. Bellow you find the instructions to install it on Mac OS X:

1. Install Homebrew;
2. brew install sdl sdl_image sdl_mixer sdl_ttf portmidi mercurial;
3. source activate aind;
4. pip install pygame.