package com.tangcz.springboot.common.data.support;

/**
 * ClassName:Tuple2
 * Package:com.tangcz.springboot.common.data.support
 * Description:
 *
 * @date:2020/6/6 14:34
 * @author:tangchengzao
 */
public class Tuple2<V1, V2> extends Tuple {

    public Tuple2() {
        super(2);
    }

    public Tuple2(V1 v1) {
        this(v1, null);
    }

    public Tuple2(V1 v1, V2 v2) {
        super(2);

        setV1(v1);
        setV2(v2);
    }

    public V1 getV1() {
        return (V1) arr[0];
    }

    public void setV1(V1 v1) {
        arr[0] = v1;
    }

    public V2 getV2() {
        return (V2) arr[1];
    }

    public void setV2(V2 v2) {
        arr[1] = v2;
    }

}
