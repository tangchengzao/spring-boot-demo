package com.tangcz.springboot.common.data.beans;

import com.tangcz.springboot.common.context.RequestContext;

/**
 * ClassName:RouteBaseDO
 * Package:com.tangcz.springboot.common.data.beans
 * Description:
 *
 * @date:2020/6/6 19:23
 * @author:tangchengzao
 */
public abstract class RouteBaseDO extends BaseDO {

    private long              routeId;

    public RouteBaseDO() {
        this.routeId = RequestContext.getRouteId();
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

}
