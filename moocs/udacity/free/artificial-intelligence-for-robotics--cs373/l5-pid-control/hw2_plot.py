import hw2

import matplotlib
import matplotlib.pyplot as plot

def add_plot(path, plot, label_txt):
    path_xs = map(lambda x: x[0], path)
    path_xs.append(path[0][0])
    path_ys = map(lambda x: x[1], path)
    path_ys.append(path[0][1])
    plot.plot(path_xs, path_ys, label="%s points" % label_txt, linestyle='none', marker='.')
    smoothed = hw2.smooth(path)
    smoothed_xs = map(lambda x: x[0], smoothed)
    smoothed_xs.append(smoothed[0][0])
    smoothed_ys = map(lambda x: x[1], smoothed)
    smoothed_ys.append(smoothed[0][1])
    plot.plot(smoothed_xs, smoothed_ys, label="%s smoothed" % label_txt)
    
add_plot(hw2.testpath1, plot, "path 1")
add_plot(hw2.testpath2, plot, "path 2")

plot.legend(loc='upper right')
plot.show()







