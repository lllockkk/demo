package logwire.web.service.dps.model;

import logwire.web.service.dps.constants.Mot;
import logwire.web.service.dps.model.base.ModelBase;

import java.math.BigDecimal;

${package}
public class Capacity extends ModelBase {
public Mot getMotEnum() {
String mot = getValue("mot");
return Mot.valueOf(mot);
}

public BigDecimal getCapacityVolume() {
return decimal("capacity_volume");
}

@Override
public Object clone() {
Capacity cloneCapacity = new Capacity();
cloneCapacity.putAll(this);
return cloneCapacity;
}
}
