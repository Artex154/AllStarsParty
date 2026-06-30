package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.manager.ItemManager;
import be.artex.allStarsParty.role.old.AOT.peak.DashPeak;
import be.artex.allStarsParty.role.old.AOT.peak.TransformationPeak;
import be.artex.allStarsParty.role.old.AOT.reiner.TransformationReiner;
import be.artex.allStarsParty.role.old.DS.muichiro.SouffleBrume;
import be.artex.allStarsParty.role.rewrited.protagoniste.all_might.OFA;
import be.artex.allStarsParty.role.rewrited.protagoniste.all_might.USOS;
import be.artex.allStarsParty.role.rewrited.antagoniste.muzan.Sang;
import be.artex.allStarsParty.role.rewrited.antagoniste.muzan.effectSelector.EffectSelector;
import be.artex.allStarsParty.role.old.HXH.kurapika.Serment;
import be.artex.allStarsParty.role.old.MHA.denki.Decharge;
import be.artex.allStarsParty.role.old.MHA.hawks.AilesDAcier;
import be.artex.allStarsParty.role.rewrited.antagoniste.sasuke.FDMO;
import be.artex.allStarsParty.role.rewrited.antagoniste.sasuke.Susano;
import be.artex.allStarsParty.role.rewrited.protagoniste.shoto.FireSide;
import be.artex.allStarsParty.role.rewrited.protagoniste.shoto.IceSide;
import be.artex.allStarsParty.role.rewrited.protagoniste.tobirama.HNJ;
import be.artex.allStarsParty.role.old.solo.BonusGoldenApples;
import be.artex.allStarsParty.role.old.solo.hisoka.JeuDeCartes;
import be.artex.allStarsParty.role.rewrited.protagoniste.tobirama.RNK;
import be.artex.allStarsParty.role.old.DS.zenitsu.FrappeFoudroyante;

public class ItemRegistry {
    public static ItemManager itemManager = new ItemManager();

    public static final ASPItem DASH_PEAK = itemManager.registerItem(new DashPeak());
    public static final ASPItem TRANSFORMATION_PEAK = itemManager.registerItem(new TransformationPeak());
    public static final ASPItem TRANSFORMATION_REINER = itemManager.registerItem(new TransformationReiner());
    public static final ASPItem SOUFFLE_BRUME = itemManager.registerItem(new SouffleBrume());
    public static final ASPItem SANG = itemManager.registerItem(new Sang());
    public static final ASPItem EFFECT_SELECTOR = itemManager.registerItem(new EffectSelector());
    public static final ASPItem SERMENT = itemManager.registerItem(new Serment());
    public static final ASPItem DECHARGE = itemManager.registerItem(new Decharge());
    public static final ASPItem AILES_ACIER = itemManager.registerItem(new AilesDAcier());
    public static final ASPItem FIRE_SIDE = itemManager.registerItem(new FireSide());
    public static final ASPItem ICE_SIDE = itemManager.registerItem(new IceSide());
    public static final ASPItem HNJ = itemManager.registerItem(new HNJ());
    public static final ASPItem RNK = itemManager.registerItem(new RNK());
    public static final ASPItem BONUS_GOLDEN_APPLES = itemManager.registerItem(new BonusGoldenApples());
    public static final ASPItem JEU_DE_CARTES = itemManager.registerItem(new JeuDeCartes());
    public static final ASPItem SUSANO = itemManager.registerItem(new Susano());
    public static final ASPItem FDMO = itemManager.registerItem(new FDMO());
    public static final ASPItem FRAPPE_FOUDROYANTE = itemManager.registerItem(new FrappeFoudroyante());
    public static final ASPItem USOS = itemManager.registerItem(new USOS());
    public static final ASPItem OFA = itemManager.registerItem(new OFA());
}
