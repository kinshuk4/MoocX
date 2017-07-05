## Using Conda To Manage Python Virtual Environments and Packages

After installing Anaconda/[Miniconda3](http://conda.pydata.org/miniconda.html) installer from Continuum Analytics, you would use the "conda", "activate", and "deactivate" commands to handle creation of virtual environments, activating/deactivating environments, and installing/uninstalling Python packages.

### Anaconda

Anaconda manages packages and environments for use with Python. It provides a wealthy of packages often used in data science work as well as virtual environments to isolate different projects. Besides helping isolate your projects, environments help you share your work with others.

When you install Anaconda, you find the following contents:

- Conda (package and environment manager);
- Python;
- More than 150 scientific packages.

### Why Anaconda?

Create environments to isolate projects that use different versions of Python and/or different packages.



## Some typical example usage

### Environment specific

**Create a new Python 3.5 virtual environment called "my_env":** 

```bash
conda create -n my_env python=3.5

```
If you want additional packages:
```bash
conda create -n mynewenviron package1 package2 etc
```

**Exporting environment - Provided the environment is selected**
```bash
conda env export > environment.yml
```
**Importing existing environment from yaml file **
```bash
conda env create --file=/path/to/envfile.yaml
```

**To activate the new virtual environment:**

```bash
activate my_env

```

**To deactive your virtual environment:**
```bash
deactivate # no need to enter environment name
```

**To remove an environment**

```bash
conda remove -n myenvirontoremove --all
```

(you can also simply delete the folder)

**Update Conda**

```bash
conda update conda
```

OR

```bash
conda update anaconda
```

**List available environments and currently activated environment**

```bash
conda info -e
```

### Package Specific

**To install Python package:**

```bash
conda install <package_name>
```

**Install a package in a given environment**

```bash
conda install -n myenv <package_name>
```

**To update a Python package:**

```bash
conda update <package_name>
```

**Update a package in given environment**

```bash
conda update -n myenv <package_name>
```

**To uninstall a Python package:**


```bash
conda uninstall <pacakge_name>
```

**To remove package from outside**

```bash
conda remove -n myenv package1
```

**To view a list of installed packages in your environment:**

```bash
conda list
```

**To test removing a package**

```bash
conda remove --dry-run -n myenv package1
```



## You may still need to use pip!

If you have a need for a Python package that is not available at Continuum Analytics, but available at [PyPi](https://pypi.python.org/pypi) repo, you can always use the pip command:

```bash
pip install <package_name>
```

**You can even install a GitHub repo package directly using pip:**

```bash
pip install git+https://github.com/django-extensions/django-extensions.git
```

**Tried to install numpy on Windows, but failed because you don't have the proper C or Fortran compilers?SOLUTION:** Download pre-compiled binaries! For pre-compiled Windows binaries, you can download them [here.](http://www.lfd.uci.edu/~gohlke/pythonlibs/)

**Then install the .whl using pip:**

```bash
pip install path_to_downloaded_whl_filebash
```

**Example:**

```bash
pip install d:\Downloads\numpy-1.10.4+mkl-cp35-cp35m-win_amd64.whl
```



## Summary

### Using Conda

```bash
conda list
conda upgrade conda
conda upgrade --all
```

### Managing Packages

```bash
conda install <package_name>=<version>, <package_name>=<version>, ...
conda remove <package_name>
conda update <package_name>
conda search <search_term>
```

### Managing Environment

```bash
conda create -n <env_name> <package_name>
source activate <env_name>
source deactivate <env_name> 

conda env export > environment.yaml
conda env create -f environment.yaml
pip freeze > requirements.txt

conda env list
conda env remove --name <env_name>
conda env export --name <env_name>
```

### Using Different Python Environment

```bash
conda create -n py2 python=2
conda create -n py3 python=3
```

## Best practices

1. Create separate environments for Python 2 and 3;
2. Usually, create environments for each project;
3. Include an environment file in every project so other contributors will easily reproduce your environment.

## 

### References

http://docs.continuum.io/conda/intro.html
http://continuum.io/blog/conda
http://continuum.io/blog/conda_packaging
http://technicaldiscovery.blogspot.nl/2013/12/why-i-promote-conda.html
https://speakerdeck.com/teoliphant/packaging-and-deployment-with-conda
http://stackoverflow.com/questions/20928566/conda-installing-local-development-package-into-single-conda-environment
https://gist.githubusercontent.com/iansheridan/870778/raw/caadec8fcbb94a5416a8298424529be4f2ef74db/git-cheat-sheet.md