package theFishing.quest.quests;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theFishing.actions.AbandonQuestAction;
import theFishing.cards.QuestTimeTheGemSearch;
import theFishing.util.TexLoader;
import theFishing.util.Wiz;

import static theFishing.FishingMod.makeImagePath;

public class ThePrismaticPortal extends AbstractQuest {
    public static final String ID = "ThePrismaticPortal";

    public boolean[] okayed = new boolean[4];

    public ThePrismaticPortal() {
        super(ID, 4);
    }

    @Override
    public String getName() {
        return "The Prismatic Portal";
    }

    @Override
    public String getDescription() {
        return "#yQuest: Play a Common, #bUncommon, #yRare, and Special card. NL #yReward: Shuffle #yQuest #yTime: #yThe #yGem #ySearch into your draw pile.";
    }

    @Override
    public void grantReward() {
        Wiz.shuffleIn(new QuestTimeTheGemSearch());
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        int rarity = card.rarity == AbstractCard.CardRarity.COMMON ? 0 : card.rarity == AbstractCard.CardRarity.UNCOMMON ? 1 : card.rarity == AbstractCard.CardRarity.RARE ? 2 : 3;
        if (!okayed[rarity]) {
            okayed[rarity] = true;
            if (okayed[0] && okayed[1] && okayed[2] && okayed[3]) {
                grantReward();
                AbstractDungeon.actionManager.addToBottom(new AbandonQuestAction(this));
            }
        }
    }

    private static Texture common_unc = TexLoader.getTexture(makeImagePath("quests/Portal_Common.png"));
    private static Texture common_com = TexLoader.getTexture(makeImagePath("quests/Portal_Common_Completed.png"));
    private static Texture uncommon_unc = TexLoader.getTexture(makeImagePath("quests/Portal_Uncommon.png"));
    private static Texture uncommon_com = TexLoader.getTexture(makeImagePath("quests/Portal_Uncommon_Completed.png"));
    private static Texture rare_unc = TexLoader.getTexture(makeImagePath("quests/Portal_Rare.png"));
    private static Texture rare_com = TexLoader.getTexture(makeImagePath("quests/Portal_Rare_Completed.png"));
    private static Texture special_unc = TexLoader.getTexture(makeImagePath("quests/Portal_Special.png"));
    private static Texture special_com = TexLoader.getTexture(makeImagePath("quests/Portal_Special_Completed.png"));

    @Override
    public Texture progressTex(int idx) {
        switch (idx) {
            case 0:
                return okayed[0] ? common_com : common_unc;
            case 1:
                return okayed[1] ? uncommon_com : uncommon_unc;
            case 2:
                return okayed[2] ? rare_com : rare_unc;
            case 3:
                return okayed[3] ? special_com : special_unc;
        }
        return super.progressTex(idx);
    }
}