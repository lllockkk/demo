package com.placeholder.jmx.MXBean;

public interface QueueSamplerMBean {
    QueueSample getQueueSample();
    void clearQueue();
}
