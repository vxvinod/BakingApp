package com.example.a60010743.bakingpro.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingWidgetRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
