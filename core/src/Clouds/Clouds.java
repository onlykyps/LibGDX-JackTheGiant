package Clouds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

/**
 * Created by claud on 05/09/2017.
 */

public class Clouds extends Sprite
{
    private World world;
    private Body body;
    private String cloudName;



    private boolean drawLeft;




    public Clouds(World world, String cloudName)
    {
        super(new Texture("D:\\Dropbox\\My Games\\Jack The Giant\\android\\assets\\4 - Clouds\\" + cloudName + ".png"));
        this.world = world;
        this.cloudName = cloudName;

    }

    void createBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set((getX()-30)/ GameInfo.PPM,getY()/GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth()/2-19)/GameInfo.PPM,(getHeight()/2-10)/GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(cloudName);

        shape.dispose();

    }

    public void setSpritePosition(float x, float y)
    {
        setPosition(x,y);
        createBody();
    }

    public String getCloudName()
    {
        return this.cloudName;

    }

    public boolean isDrawLeft() {
        return drawLeft;
    }

    public void setDrawLeft(boolean drawLeft) {
        this.drawLeft = drawLeft;
    }







} //clouds
