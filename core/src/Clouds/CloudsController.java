package clouds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.bcel.internal.generic.FieldObserver;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Random;

import Clouds.Clouds;
import Collectables.collectables;
import helpers.GamaManager;
import player.player;
import helpers.GameInfo;

/**
 * Created by claud on 06/09/2017.
 */

public class CloudsController
{
    private World world;

    private Array<Clouds> clouds = new Array<Clouds>();
    private Array<collectables> collectables = new Array<Collectables.collectables>();

    private final float DISTANCE_BETWEEN_CLOUDS = 250F;

    private float minX, maxX;

    private float lastCloudPositionY;

    private float cameraY;

    private Random random = new Random();






    public CloudsController(World world)
    {
        this.world=world;
        minX = GameInfo.width/2-110;
        maxX = GameInfo.width/2+110;
        createClouds();
        positionClouds(true);
    }

    void createClouds()
    {
        for (int i=0; i<2;i++)
        {
            clouds.add(new Clouds(world,"Dark Cloud"));
        }

        int index = 1;

        for (int i =0;i<6;i++)
        {
            clouds.add(new Clouds(world, "Cloud " +index));
            index++;

            if (index==4)
            {
                index=1;
            }
        }

        clouds.shuffle();
    }

    public void positionClouds(boolean firstTimeArranging)
    {
        while (clouds.get(0).getCloudName() == "Dark Cloud")
        {
            clouds.shuffle();

        }

        float positionY = 0;

        if (firstTimeArranging)
        {
            positionY = GameInfo.height/2f;
        }
        else
        {
            positionY = lastCloudPositionY;
        }

        int controlX = 0;

        for (Clouds c: clouds)
        {

            if (c.getX()==0 && c.getY()==0)
            {
                float tempX = 0;
                if (controlX==0)
                {
                    tempX = randomBteweenNumbers(maxX-60,maxX);
                    controlX = 1;
                    c.setDrawLeft(false);
                }
                else if (controlX==1)
                {
                    tempX=randomBteweenNumbers(minX+60,minX);
                    controlX=0;
                    c.setDrawLeft(true);
                }
                c.setSpritePosition(tempX,positionY);
                positionY -=DISTANCE_BETWEEN_CLOUDS;
                lastCloudPositionY = positionY;

                if(!firstTimeArranging && c.getCloudName()!="Dark Cloud")
                {
                    int rand = random.nextInt(10);

                    if (rand>5)
                    {
                        //spawn collectable items

                        int randomCollectable = random.nextInt(2);

                        if (randomCollectable ==0)
                        {
                            //spawn life if life count < 2

                            if (GamaManager.getInstance().lifeScore<2)
                            {
                                collectables collectable = new collectables(world, "Life");
                                collectable.setCollectablePosition(c.getX(), c.getY()+40);

                                collectables.add(collectable);
                            }
                            else
                            {
                                collectables collectable = new collectables(world, "Coin");
                                collectable.setCollectablePosition(c.getX(), c.getY()+40);

                                collectables.add(collectable);
                            }


                        }
                        else
                        {
                            //spawn coin
                            collectables collectable = new collectables(world, "Coin");
                            collectable.setCollectablePosition(c.getX(), c.getY()+40);

                            collectables.add(collectable);
                        }
                    }

                }

            }


        }



    }

    public void drawClouds(SpriteBatch batch)
    {
        for (Clouds c: clouds)
        {
            if (c.isDrawLeft())
            {
                batch.draw(c,c.getX()-c.getWidth()/2f-16,c.getY()-c.getHeight()/2f+2);
            }
            else
            {
                batch.draw(c,c.getX()-c.getWidth()/2f+10,c.getY()-c.getHeight()/2f+2);
            }
        }
    }

    public void drawCollectables(SpriteBatch batch)
    {
        for(collectables c : collectables)
        {
            c.updateCollectable();
            batch.draw(c, c.getX(), c.getY());
        }
    }

    public void removeCollectables()
    {
        for (int i =0; i<collectables.size;i++)
        {
            if (collectables.get(i).getFixture().getUserData() == "Remove")
            {
                collectables.get(i).changeFilter();
                collectables.get(i).getTexture().dispose();
                collectables.removeIndex(i);
                //System.out.println("The collectable has been removed");


            }
        }
    }

    public void createAndArrangeNewClouds()
    {
        for (int i=0;i<clouds.size;i++)
        {
            if ((clouds.get(i).getY() - GameInfo.height/2-5)>cameraY)
            //cloud is out of bounds, delete it
            {
                clouds.get(i).getTexture().dispose();
                clouds.removeIndex(i);
            }
        }

        if (clouds.size == 4)
        {
            createClouds();
            positionClouds(false);
        }
    }

    public void removeOffScreenCollectables()
    {
        for (int i=0;i<collectables.size;i++)
        {
            if (collectables.get(i).getY() - GameInfo.height/2f-15>cameraY)
            {
                collectables.get(i).getTexture().dispose();
                clouds.removeIndex(i);
            }
        }
    }

    public void setCameraY (float cameraY)
    {
        this.cameraY = cameraY;
    }

    public player positionThePlayer (player player)
    {
        player = new player (world,clouds.get(0).getX(),clouds.get(0).getY()+78);

        return player;
    }

    private float randomBteweenNumbers(float min, float max)
    {

        return random.nextFloat()*(max-min)+min;
    }





} //clouds controller
