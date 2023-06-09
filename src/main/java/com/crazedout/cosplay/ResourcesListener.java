package com.crazedout.cosplay;

import com.crazedout.cosplay.adapter.android.Resources;

public interface ResourcesListener {

    public void beginLoading(Resources res, Object resource);
    public void doneLoading(Resources res, Object resource);


}
