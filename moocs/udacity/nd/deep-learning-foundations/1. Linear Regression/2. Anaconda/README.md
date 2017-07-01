## Anaconda 
A distribution of packages built for data science, includes conda (package and environment manager).

# Why Anaconda?
Create environments to isolate projects that use different versions of Python and/or different packages.

# Using Conda
```bash
conda list
conda upgrade conda
conda upgrade --all
```

# Managing Packages
```bash
conda install <package_name>=<version>, <package_name>=<version>, ...
conda remove <package_name>
conda update <package_name>
conda search <search_term>
```

# Managing Environment
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

# Using Different Python Environment
```bash
conda create -n py2 python=2
conda create -n py3 python=3
```