package io.aos.tron.t2;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

public class NNPlayer extends Player
{

    void allocateSpace()
    {
        in = new double[8];
        hid = new double[numHidden];
        hid_sum = new double[numHidden];
        out = new double[3];
        OB = new double[3];
        HB = new double[numHidden];
        OH = new double[3][numHidden];
        HI = new double[numHidden][8];
    }

    public NNPlayer()
    {
        sensor = new int[8];
        compass = 0;
        skip = 0;
        allocateSpace();
    }

    public NNPlayer(String s, Color color, Arena arena, int i, int j, byte byte0, String s1)
    {
        sensor = new int[8];
        compass = 0;
        skip = 0;
        name = s;
        this.color = color;
        this.arena = arena;
        x_max = i;
        y_max = j;
        player_no = byte0;
        allocateSpace();
        if(s1 != null)
            readNetwork(s1);
    }

    public void go(int i, int j)
    {
        super.go(i, j);
        skip = 0;
    }

    public int whereDoIGo()
    {
        int i = d;
        if(++skip < 3)
            return d;
        skip = 0;
        computeSensors(x0, y0);
        for(int j = 0; j < 8; j++)
            in[j] = (double)sensor[j] / 256D;

        for(int k = 0; k < numHidden; k++)
        {
            hid_sum[k] = HB[k];
            for(int j1 = 0; j1 < 8; j1++)
                hid_sum[k] += HI[k][j1] * in[j1];

        }

        for(int l = 0; l < numHidden; l++)
            hid[l] = my_tanh(hid_sum[l]);

        for(int i1 = 0; i1 < 3; i1++)
        {
            out[i1] = OB[i1];
            for(int k1 = 0; k1 < numHidden; k1++)
                out[i1] += OH[i1][k1] * hid[k1];

        }

        byte byte1;
        if(out[1] > out[0])
        {
            if(out[2] > out[1])
            {
                byte1 = 1;
                didsomething = true;
            } else
            {
                byte1 = 0;
                didsomething = false;
            }
        } else
        if(out[2] > out[0])
        {
            byte1 = 1;
            didsomething = true;
        } else
        {
            byte1 = -1;
            didsomething = true;
        }
        byte byte0;
        switch(byte1)
        {
        case -1: 
            byte0 = -1;
            break;

        case 1: // '\001'
            byte0 = 1;
            break;

        case 0: // '\0'
            byte0 = 0;
            break;

        default:
            byte0 = 0;
            System.err.println("Error: move=" + byte1 + "in gp");
            break;
        }
        i = (d + byte0 + 4) % 4;
        return i;
    }

    public int sense(int i, int j, int k, int l)
    {
        int i1 = 0;
        do
        {
            i += k;
            if(i == x_max)
                i = 0;
            else
            if(i == -1)
                i = x_max - 1;
            j += l;
            if(j == y_max)
                j = 0;
            else
            if(j == -1)
                j = y_max - 1;
            if(!arena.board[i][j])
                i1++;
            else
                return x_max - i1;
        } while(true);
    }

    public void computeSensors(int i, int j)
    {
        int k;
        if(compass == 1)
            k = 0;
        else
            k = 2 * d;
        sensor[((0 - k) + 8) % 8] = sense(i, j, 0, 1);
        sensor[((1 - k) + 8) % 8] = sense(i, j, 1, 1);
        sensor[((2 - k) + 8) % 8] = sense(i, j, 1, 0);
        sensor[((3 - k) + 8) % 8] = sense(i, j, 1, -1);
        sensor[((4 - k) + 8) % 8] = sense(i, j, 0, -1);
        sensor[((5 - k) + 8) % 8] = sense(i, j, -1, -1);
        sensor[((6 - k) + 8) % 8] = sense(i, j, -1, 0);
        sensor[((7 - k) + 8) % 8] = sense(i, j, -1, 1);
    }

    void setRandom(long l, double d, double d1)
    {
        Random random = new Random(l);
        if(numHidden > 0)
        {
            for(int i = 0; i < numHidden; i++)
            {
                HB[i] = d1 * random.nextGaussian();
                for(int i1 = 0; i1 < 8; i1++)
                    HI[i][i1] = d * random.nextGaussian();

            }

            for(int j = 0; j < 3; j++)
            {
                OB[j] = d1 * random.nextGaussian();
                for(int j1 = 0; j1 < numHidden; j1++)
                    OH[j][j1] = d * random.nextGaussian();

            }

        } else
        {
            for(int k = 0; k < 3; k++)
            {
                OB[k] = d1 * random.nextGaussian();
                for(int k1 = 0; k1 < 8; k1++)
                    OI[k][k1] = d * random.nextGaussian();

            }

        }
    }

    void adjustWeights(NNPlayer nnplayer, double d)
    {
        if(numHidden > 0)
        {
            for(int i = 0; i < numHidden; i++)
            {
                HB[i] += d * nnplayer.HB[i];
                for(int l = 0; l < 8; l++)
                    HI[i][l] += d * nnplayer.HI[i][l];

            }

            for(int j = 0; j < 3; j++)
            {
                OB[j] += d * nnplayer.OB[j];
                for(int i1 = 0; i1 < numHidden; i1++)
                    OH[j][i1] += d * nnplayer.OH[j][i1];

            }

        } else
        {
            for(int k = 0; k < 3; k++)
            {
                OB[k] += d * nnplayer.OB[k];
                for(int j1 = 0; j1 < 8; j1++)
                    OI[k][j1] += d * nnplayer.OI[k][j1];

            }

        }
    }

    void readNetwork(String s)
    {
        try
        {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(s));
            boolean flag = true;
            numHidden = 5;
            allocateSpace();
            datainputstream.readLine();
            datainputstream.readLine();
            datainputstream.readLine();
            datainputstream.readLine();
            if(numHidden > 0)
            {
                for(int i = 0; i < numHidden; i++)
                {
                    HB[i] = getFormattedDouble(datainputstream);
                    for(int l = 0; l < 8; l++)
                        HI[i][l] = getFormattedDouble(datainputstream);

                }

                for(int j = 0; j < 3; j++)
                {
                    OB[j] = getFormattedDouble(datainputstream);
                    for(int i1 = 0; i1 < numHidden; i1++)
                        OH[j][i1] = getFormattedDouble(datainputstream);

                }

            } else
            {
                for(int k = 0; k < 3; k++)
                {
                    OB[k] = getFormattedDouble(datainputstream);
                    for(int j1 = 0; j1 < 8; j1++)
                        OI[k][j1] = getFormattedDouble(datainputstream);

                }

            }
        }
        catch(FileNotFoundException filenotfoundexception)
        {
            System.out.println("error reading weights from file: " + filenotfoundexception);
            System.exit(0);
        }
        catch(IOException ioexception)
        {
            System.out.println("error reading weights from file: " + ioexception);
            System.exit(0);
        }
    }

    double getFormattedDouble(DataInputStream datainputstream) throws IOException
    {
        String s;
        while((s = datainputstream.readLine()).trim().length() == 0) ;
        return Double.valueOf(s).doubleValue();
    }

    void printNetwork(PrintStream printstream)
    {
        printstream.println("uNumHidden\t" + numHidden);
        printstream.println("\nweights\n");
        if(numHidden > 0)
        {
            for(int i = 0; i < numHidden; i++)
            {
                printstream.println(HB[i]);
                for(int l = 0; l < 8; l++)
                    printstream.println(HI[i][l]);

                printstream.println();
            }

            for(int j = 0; j < 3; j++)
            {
                printstream.println(OB[j]);
                for(int i1 = 0; i1 < numHidden; i1++)
                    printstream.println(OH[j][i1]);

                printstream.println();
            }

        } else
        {
            for(int k = 0; k < 3; k++)
            {
                printstream.println(OB[k]);
                for(int j1 = 0; j1 < 8; j1++)
                    printstream.println(OI[k][j1]);

                printstream.println();
            }

        }
        printstream.println();
    }

    void copyWeights(NNPlayer nnplayer)
    {
        if(numHidden > 0)
        {
            for(int i = 0; i < numHidden; i++)
            {
                HB[i] = nnplayer.HB[i];
                for(int l = 0; l < 8; l++)
                    HI[i][l] = nnplayer.HI[i][l];

            }

            for(int j = 0; j < 3; j++)
            {
                OB[j] = nnplayer.OB[j];
                for(int i1 = 0; i1 < numHidden; i1++)
                    OH[j][i1] = nnplayer.OH[j][i1];

            }

        } else
        {
            for(int k = 0; k < 3; k++)
            {
                OB[k] = nnplayer.OB[k];
                for(int j1 = 0; j1 < 8; j1++)
                    OI[k][j1] = nnplayer.OI[k][j1];

            }

        }
    }

    static double my_tanh(double d)
    {
        if(d < 0.0D)
        {
            double d1 = Math.exp(2D * d);
            return (d1 - 1.0D) / (d1 + 1.0D);
        } else
        {
            double d2 = Math.exp(-2D * d);
            return (1.0D - d2) / (1.0D + d2);
        }
    }

    public static final int CMD_LEFT_TURN = -1;
    public static final int CMD_RIGHT_TURN = 1;
    public static final int CMD_STRAIGHT = 0;
    public static final int CMD_ERROR = 9;
    public static final int CNB_SENSORS = 8;
    public int sensor[];
    public int compass;
    public int skip;
    public static final int toSkip = 3;
    public int robot_id;
    public int gen_no;
    static final int numInput = 8;
    static int numHidden = 5;
    static final int numOutput = 3;
    boolean didsomething;
    double in[];
    double hid[];
    double hid_sum[];
    double out[];
    double OB[];
    double HB[];
    double OH[][];
    double HI[][];
    double OI[][];

}
