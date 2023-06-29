package com.onbelay.dagserverapp.graphnode.subscribe.snapshot;


import com.onbelay.core.entity.snapshot.AbstractSnapshot;

public class SubGraphNodeSnapshot extends AbstractSnapshot {

    private SubGraphNodeDetail detail = new SubGraphNodeDetail();

    public SubGraphNodeDetail getDetail() {
        return detail;
    }

    public void setDetail(SubGraphNodeDetail detail) {
        this.detail = detail;
    }
}
