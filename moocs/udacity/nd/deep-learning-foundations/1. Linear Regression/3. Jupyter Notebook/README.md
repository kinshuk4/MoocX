# Jupyter Notebook
A web application that allows us to combine explanatory text, math equations, code, and visualizations all in one easily sharable document. It is a form of literate programming:  the documentation is written as a narrative alongside the code. 

List of available kernels: https://github.com/jupyter/jupyter/wiki/Jupyter-kernels

## Examples 
- Gravitational waves from two colliding blackholes: https://losc.ligo.org/s/events/GW150914/GW150914_tutorial.html
- Machine Learning: http://nbviewer.jupyter.org/github/masinoa/machine_learning/tree/master/
- Body Fat Percentage: https://github.com/mcleonard/blog_posts/blob/master/body_fat_percentage.ipynb

## Installing
```bash
conda install jupyter notebook
pip install jupyter notebook

jupyter notebook

jupyter nbconvert --to html notebook.ipynb
jupyter nbconvert notebook.ipynb --to slides
```

## Resources
- Magic Commands: http://ipython.readthedocs.io/en/stable/interactive/magics.html
    - %pdb
    - %matplotlib inline
    - %%timeit