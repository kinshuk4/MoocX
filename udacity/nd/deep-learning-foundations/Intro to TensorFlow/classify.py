#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Mar  2 16:44:03 2017

@author: philippew
"""
import numpy as np
import tensorflow as tf

def run():
    output = None
    logit_data = np.asarray(0.1) * [2.0, 1.0, 0.1]
    logits = tf.placeholder(tf.float32)
    
    # TODO: Calculate the softmax of the logits
    softmax = tf.nn.softmax(logits)   
    
    with tf.Session() as sess:
        # TODO: Feed in the logit data
        output = sess.run(softmax, feed_dict={logits : logit_data} )

    return output

print(run())