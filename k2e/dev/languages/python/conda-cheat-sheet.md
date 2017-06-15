# Using Conda To Manage Python Virtual Environments and Packages

After installing Anaconda/[Miniconda3](http://conda.pydata.org/miniconda.html) installer from Continuum Analytics, you would use the "conda", "activate", and "deactivate" commands to handle creation of virtual environments, activating/deactivating environments, and installing/uninstalling Python packages.

## Some typical example usage:

** Create a new Python 3.5 virtual environment called "my_env": **

```
conda create -n my_env python=3.5

```
If you want additional packages:
```
conda create -n mynewenviron package1 package2 etc
```

** Exporting environment - Provided the environment is selected**
```
conda env export > environment.yml
```
**  Importing existing environment from yaml file **
```
conda env create --file=/path/to/envfile.yaml
```

**To activate the new virtual environment:**

```
activate my_env

```

**To install Python package:**

```
conda install <package_name>

```

**To update a Python package:**

```
conda update <package_name>

```

**To uninstall a Python package:**


```
conda uninstall <pacakge_name>

```

**To view a list of installed packages in your environment:**

```
conda list

```

**To deactive your virtual environment:**
```
deactivate # no need to enter environment name

```

**To uninstall a Python package:**

## You may still need to use pip!

If you have a need for a Python package that is not available at Continuum Analytics, but available at [PyPi](https://pypi.python.org/pypi) repo, you can always use the pip command:

```
pip install <package_name>

```

**You can even install a GitHub repo package directly using pip:**

```
pip install git+https://github.com/django-extensions/django-extensions.git

```

**Tried to install numpy on Windows, but failed because you don't have the proper C or Fortran compilers?SOLUTION:** Download pre-compiled binaries! For pre-compiled Windows binaries, you can download them [here.](http://www.lfd.uci.edu/~gohlke/pythonlibs/)

**Then install the .whl using pip:**

```
pip install path_to_downloaded_whl_file

```

**Example:**

```
pip install d:\Downloads\numpy-1.10.4+mkl-cp35-cp35m-win_amd64.whl
```





to remove an environment

  conda remove -n myenvirontoremove --all
  
always start with

  conda update conda

or run

  conda update anaconda


to test removing a package

  conda remove --dry-run -n myenv package1


to remove a package

  conda remove -n myenv package1


to remove an entire environment

  conda remove -n test --all (you can also simply delete the folder)


install a package in the base environment

  conda install ipython-notebook


install a package in a given environment

  conda install -n myenv ipython-notebook


update a package

  conda update -n myenv mypackage


display summary info about conda setup

  conda info


list available environments and currently activated environment

  conda info -e


display installed packages in currently activated environment and write to file

  conda list -e > requirements.txt


display installed packages in given environment

  conda list -e -n myenv


References
==========
http://docs.continuum.io/conda/intro.html
http://continuum.io/blog/conda
http://continuum.io/blog/conda_packaging
http://technicaldiscovery.blogspot.nl/2013/12/why-i-promote-conda.html
https://speakerdeck.com/teoliphant/packaging-and-deployment-with-conda
http://stackoverflow.com/questions/20928566/conda-installing-local-development-package-into-single-conda-environment
https://gist.githubusercontent.com/iansheridan/870778/raw/caadec8fcbb94a5416a8298424529be4f2ef74db/git-cheat-sheet.md