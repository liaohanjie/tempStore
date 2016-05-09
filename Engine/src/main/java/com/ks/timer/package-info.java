/*
 * 时间控制，主要分为Job和Task。
 */


/**
 * 定时运行一段脚本。
 * 每一个Job必须继承{@link com.ks.timer.job.BaseJob}并注释运行规则规则格式参见{@link com.ks.timer.job.Job}。
 * 每一个Task必须继承{@link com.ks.timer.task.BaseTask}并注解运行规则规则参见{@link com.ks.timer.task.Task}。
 */
package com.ks.timer;