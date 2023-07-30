package theFishing.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theFishing.cards.AbstractFishingCard;
import theFishing.util.Wiz;

import java.util.ArrayList;

import static theFishing.FishingMod.makeID;
import static theFishing.util.Wiz.*;

public class ZaHando extends AbstractFishingCard {
    public final static String ID = makeID("ZaHando");
    // intellij stuff attack, enemy, uncommon, 12, 4, , , , 

    public ZaHando() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (!p.hand.isEmpty()) {
                    if (p.hand.size() % 2 == 0) {
                        att(new ExhaustSpecificCardAction(p.hand.group.get(p.hand.group.size() / 2), p.hand));
                        att(new ExhaustSpecificCardAction(p.hand.group.get(p.hand.group.size() / 2 - 1), p.hand));
                    } else {
                        att(new ExhaustSpecificCardAction(p.hand.group.get(p.hand.group.size() / 2), p.hand));
                    }
                }
            }
        });
    }

    @Override
    public void hover() {
        super.hover();
        if (Wiz.isInCombat()) {
            for (AbstractCard q : getMiddleCards()) {
                q.glowColor = Color.RED.cpy();
                q.beginGlowing();
            }
        }
    }

    //TODO: Make it not card glow, instead do special sparkles

    @Override
    public void unhover() {
        super.unhover();
        if (Wiz.isInCombat()) {
            for (AbstractCard q : getMiddleCards()) {
                q.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
                q.triggerOnGlowCheck();
            }
            AbstractDungeon.player.hand.applyPowers();
        }
    }

    private ArrayList<AbstractCard> getMiddleCards() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> middleCards = new ArrayList<>();
        for (AbstractCard q : p.hand.group) {
            if (q != this)
                middleCards.add(q);
        }
        ArrayList<AbstractCard> coolStuffList = new ArrayList<>();
        if (!middleCards.isEmpty()) {
            if (middleCards.size() % 2 == 0) {
                coolStuffList.add(middleCards.get(middleCards.size() / 2 - 1));
                coolStuffList.add(middleCards.get(middleCards.size() / 2));
            } else {
                coolStuffList.add(middleCards.get(middleCards.size() / 2));
            }
        }
        return coolStuffList;
    }

    public void upp() {
        upgradeDamage(4);
    }
}