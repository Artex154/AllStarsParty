package be.artex.allStarsParty.role.rewrited.DS.muzan.effectSelector;

public class EffectSelectorHolder {
    public boolean strengthEffect;
    public boolean speedEffect;
    public boolean resistanceEffect;

    public EffectSelectorHolder(boolean strengthEffect, boolean speedEffect, boolean resistanceEffect) {
        this.strengthEffect = strengthEffect;
        this.speedEffect = speedEffect;
        this.resistanceEffect = resistanceEffect;
    }

    public EffectSelectorHolder() {
        this.strengthEffect = true;
        this.speedEffect = true;
        this.resistanceEffect = true;
    }

}
