package io.aos.tron.t2;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class GPPlayer extends Player
{

    public GPPlayer(String s, Color color, Arena arena, int i, int j, byte byte0, String s1)
    {
        sensor = new int[8];
        compass = 0;
        skip = 0;
        array_terminals = new double[512];
        constants = new char[1];
        sexpr = new byte[2048];
        program = new byte[2048];
        has_program = false;
        stack_val = new double[61];
        name = s;
        this.color = color;
        this.arena = arena;
        x_max = i;
        y_max = j;
        player_no = byte0;
        if(s1 != null)
            getStrategy(s1);
    }

    public void go(int i, int j)
    {
        super.go(i, j);
        skip = 0;
    }

    public void getStrategy(String s)
    {
        for(int i = 0; i < 1; i++)
            array_terminals[i] = constants[i];

        for(int j = 0; j < 64; j++)
            array_terminals[j + 64] = 0.015873015873015872D * (double)j + 0.0D;

        try
        {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(s));
            byte byte0;
            for(robot_id = 0; (byte0 = datainputstream.readByte()) != 127; robot_id = robot_id * 10 + Character.digit((char)byte0, 10));
            byte byte1;
            for(gen_no = 0; (byte1 = datainputstream.readByte()) != 127; gen_no = gen_no * 10 + Character.digit((char)byte1, 10));
            byte byte2;
            int l;
            for(l = 0; (byte2 = datainputstream.readByte()) != 127; l = l * 10 + Character.digit((char)byte2, 10));
            for(int k = 0; k < l; k++)
            {
                byte byte3 = datainputstream.readByte();
                program[k] = byte3;
            }

            if(program[l - 1] != 127)
                System.err.println("Error: Unterminated Program len=" + l);
        }
        catch(IOException ioexception)
        {
            System.err.println("IOException: " + ioexception);
        }
        name = String.valueOf(robot_id);
        has_program = true;
    }

    public int whereDoIGo()
    {
        int k = d;
        if(++skip < 3)
            return d;
        skip = 0;
        computeSensors(x0, y0);
        for(int i = 0; i < 8; i++)
            array_terminals[1 + i] = (double)sensor[i] / 256D;

        int j = gp_program();
        byte byte0;
        switch(j)
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
            System.err.println("Error: move=" + j + "in gp");
            break;
        }
        k = (d + byte0 + 4) % 4;
        return k;
    }

    public int gp_program()
    {
        int i = 0;
        double d = 0.0D;
        int j = 0;
        ptr_stack_val = 0;
        boolean flag = true;
        char c;
        do
        {
            c = (char)program[i];
            i++;
            if(c < '}')
            {
                if(c == '|')
                {
                    char c1 = (char)program[i];
                    i++;
                    d = array_terminals[c1];
                } else
                {
                    if(c == '\004')
                        return -1;
                    if(c == '\005')
                        return 1;
                    double d3 = stack_val[--ptr_stack_val];
                    double d1 = stack_val[--ptr_stack_val];
                    switch(c)
                    {
                    default:
                        break;

                    case 0: // '\0'
                        d = d1 + d3;
                        break;

                    case 1: // '\001'
                        d = d1 - d3;
                        break;

                    case 2: // '\002'
                        d = d1 * d3;
                        break;

                    case 3: // '\003'
                        if(d3 > 1.0000000000000001E-05D || d3 < -1.0000000000000001E-05D)
                        {
                            d = d1 / d3;
                        } else
                        {
                            d = 1.0D;
                            boolean flag1 = false;
                        }
                        break;

                    case 123: // '{'
                        d = -1D;
                        if(d1 <= d3)
                            d = 1.0D;
                        break;
                    }
                }
                stack_val[ptr_stack_val++] = d;
            } else
            {
                j = program[i];
                i++;
                j = j << 8 | program[i] & 0xff;
                i++;
                j = j << 8 | program[i] & 0xff;
                i++;
                j = j << 8 | program[i] & 0xff;
                i++;
            }
            switch(c)
            {
            case 126: // '~'
                double d2 = stack_val[--ptr_stack_val];
                if(d2 < 0.0D)
                    i = j;
                break;

            case 125: // '}'
                i = j;
                break;
            }
        } while(c != '\177');
        return 0;
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

    public static final int PROC_DB = 0;
    public static final int MAX_INIT_DEPTH = 7;
    public static final int MAX_DEPTH = 17;
    public static final int MAX_ARITY = 4;
    public static final int MAX_TERMINAL = 512;
    public static final int S_EXP_MAX_LENGTH = 512;
    public static final int MAX_PROGRAM_LENGTH = 2048;
    public static final int MAX_STR_LENGTH = 16132;
    public static final int MAX_POPULATION_SIZE = 500;
    public static final int CMD_LEFT_TURN = -1;
    public static final int CMD_RIGHT_TURN = 1;
    public static final int CMD_STRAIGHT = 0;
    public static final int CMD_ERROR = 9;
    public static final int CNB_SENSORS = 8;
    public static final int ID_OP = 0;
    public static final int ID_TERM = 128;
    public static final int ID_TREE_OP = 0;
    public static final int ID_TREE_TERM = 0x80000000;
    public static final int ST_REDUCE = 0;
    public static final int ST_SHIFT = 1;
    public static final int ST_END = 2;
    public static final int OP_PLUS = 0;
    public static final int OP_MINUS = 1;
    public static final int OP_MULT = 2;
    public static final int OP_DIV = 3;
    public static final int OP_LEFT_TURN = 4;
    public static final int OP_RIGHT_TURN = 5;
    public static final int OP_IFLTE = 6;
    public static final int NB_OPERATOR = 7;
    public static final int TERM_RAND = 0;
    public static final int INST_STOP = 127;
    public static final int INST_IFGOTO = 126;
    public static final int INST_GOTO = 125;
    public static final int INST_PUSH = 124;
    public static final int INST_TEST_LTE = 123;
    public static final double EPSILON = 1.0000000000000001E-05D;
    public static final double MAX_TRIG_RANGE = 10000D;
    public static final int NB_CONSTANTS = 1;
    public static final int NB_VARIABLES = 8;
    public static final int NB_FUNCTIONS = 7;
    public static final int NB_RAND_VALUES = 64;
    public static final double INF_RANGE_RAND_VALUES = 0D;
    public static final double SUP_RANGE_RAND_VALUES = 1D;
    public static final int FIRST_INDEX_RAND_VALUES = 64;
    public static final int NB_TERMINALS = 9;
    public int sensor[];
    public int compass;
    public int skip;
    public static final int toSkip = 3;
    public double array_terminals[];
    public char constants[];
    public byte sexpr[];
    public byte program[];
    public boolean has_program;
    public int robot_id;
    public int gen_no;
    public double stack_val[];
    public int ptr_stack_val;
}
