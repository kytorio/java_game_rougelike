package com.tedu.controller;

import com.tedu.element.Direction;
import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.Role;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.util.List;
import java.util.PriorityQueue;

/**
 * 简单的向量
 */
class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dot(Vector other) {
        return this.x * other.x + this.y * other.y;
    }

    public double angleCosSquare(Vector other, double disSquare) {
        double dotValue = this.dot(other);
        return dotValue * dotValue / disSquare;
    }

    public double angleCosSquare(Vector other) {
        double xDiff = this.x - other.x;
        double yDiff = this.y - other.y;
        double disS = xDiff * xDiff + yDiff * yDiff;
        return angleCosSquare(other, disS);
    }
}

/**
 * 简单的点, 带有这个点的代价
 */
class Point implements Comparable<Point> {
    public int x;
    public int y;

    private static final double ROOT_TWO      = Math.sqrt(2.0);
    public  static final double COST_DIAGONAL = ROOT_TWO;
    public  static final double COST_LINE     = 1.0;
    private static final double K = 0.5;
    private static final double G_FACTOR = K;
    private static final double H_FACTOR = 1.0 - K;
    public  double gCost;
    public  double hCost;
    private double cost;

    public Point parent = null;

    public Point(int x, int y, double cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public void setGCost(double gCost) {
        this.gCost = gCost;
        this.setCost();
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
        this.setCost();
    }

    public double getCost() {
        return cost;
    }

    public void setParent(Point parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    @Override
    public int compareTo(Point other) {
        return Double.compare(other.cost, this.cost);
    }

    /**
     * 计算两点之间的对角距离
     */
    public double diagonalDistance(Point other) {
        //---o
        //d   \
        //x    \
        //--|   ---|*
        //     dy
        // diagDis = (dx + dy) - 2 * min(dx, dy) + sqrt(2) * min(dx, dy)
        //         = (dx + dy) - (2 - sqrt(2)) * min(dx, dy)
        //         = (dx + dy) + (sqrt(2) - 2) * min(dx, dy)
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        return dx + dy + (ROOT_TWO - 2) * Math.min(dx, dy);
    }

    /**
     * 计算两点之间的欧式距离
     */
    public double euclideanDistance(Point other) {
        double xDiff = this.x - other.x;
        double yDiff = this.y - other.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    private void setCost() {
        this.cost = G_FACTOR * this.gCost + H_FACTOR * this.hCost;
    }
}

/**
 * 小怪的 AI, 用于实现小怪的:
 * 1. 视野
 * 2. 待机行为
 * 3. 仇恨
 * 4. 自动追踪
 * 5. 自动开火
 */
public class AI {
    private static final ElementManager EM = ElementManager.getManager();

    /**
     * 仇恨对象
     */
    private Role hateRole = null;
    /**
     * 仇恨时间(ms)
     */
    private final int hateTime;
    private int currentHateTime = 0;
    private int lastGameTime = 0;
    /**
     * 视野: (angle, distance)
     * 视角: angle
     * 视距: distance
     */
    private final double angleCos;  // 视角的余弦值
    private final double distance;
    private final double angS;      // angleCos * angleCos
    private final double disS;      // distance * distance
    /**
     * 预定义方向向量(normalized)
     * 0 ---------> x
     * |  NW  N  NE
     * |   o  o  o
     * | W o  *  o E
     * |   o  o  o
     * |  SW  S  SE
     * v
     * y
     */
    private static final double HAFT_ROOT_TWO = Math.sqrt(2.0) / 2.0;
    private static final Vector V_N  = new Vector( 0.0, -1.0);
    private static final Vector V_S  = new Vector( 0.0,  1.0);
    private static final Vector V_E  = new Vector( 1.0,  0.0);
    private static final Vector V_W  = new Vector(-1.0,  0.0);
    private static final Vector V_NW = new Vector(-HAFT_ROOT_TWO, -HAFT_ROOT_TWO);
    private static final Vector V_NE = new Vector( HAFT_ROOT_TWO, -HAFT_ROOT_TWO);
    private static final Vector V_SW = new Vector(-HAFT_ROOT_TWO,  HAFT_ROOT_TWO);
    private static final Vector V_SE = new Vector( HAFT_ROOT_TWO,  HAFT_ROOT_TWO);
    private static final Vector[] VECTORS = {
            V_N,  V_S,  V_E,  V_W,
            V_NW, V_NE, V_SW, V_SE
    };
    /**
     * 预定义方向向量(denormalized)
     */
    private static final Vector VUN_N  = new Vector( 0.0, -1.0);
    private static final Vector VUN_S  = new Vector( 0.0,  1.0);
    private static final Vector VUN_E  = new Vector( 1.0,  0.0);
    private static final Vector VUN_W  = new Vector(-1.0,  0.0);
    private static final Vector VUN_NW = new Vector(-1.0, -1.0);
    private static final Vector VUN_NE = new Vector( 1.0, -1.0);
    private static final Vector VUN_SW = new Vector(-1.0,  1.0);
    private static final Vector VUN_SE = new Vector( 1.0,  1.0);

    private static final int    D_N  = Direction.UP   | Direction.NONE;
    private static final int    D_S  = Direction.DOWN | Direction.NONE;
    private static final int    D_E  = Direction.NONE | Direction.RIGHT;
    private static final int    D_W  = Direction.NONE | Direction.LEFT;
    private static final int    D_NW = Direction.UP   | Direction.LEFT;
    private static final int    D_NE = Direction.UP   | Direction.RIGHT;
    private static final int    D_SW = Direction.DOWN | Direction.LEFT;
    private static final int    D_SE = Direction.DOWN | Direction.RIGHT;
    /**
     * 仇恨对象在视野中: true
     */
    private boolean inHorizon;

    /**
     * 创建一个小怪的 AI
     * @param angleCos 视角的余弦值
     * @param distance 视距(以单元格为单位)
     * @param hateTime 仇恨时间
     */
    public AI(double angleCos, double distance, int hateTime) {
        this.angleCos = angleCos;
        this.distance = distance;
        this.hateTime = hateTime;
        this.disS     = this.distance * this.distance;
        this.angS     = this.angleCos * this.angleCos;
    }

    public void control(Enemy thisEnemy, int gameTime) {
        // 1. 更新计时器
        int deltaTime = gameTime - this.lastGameTime;
        this.lastGameTime = gameTime;
        // 2. 检查是否有仇恨对象
        if (this.hateRole != null) {
            // 有仇恨对象
            // 2.1 尝试再次仇恨该对象
            if (!this.tryHate(thisEnemy, this.hateRole)) {
                // 仇恨失败, 表明该对象离开小怪的视野, 则开始更新仇恨时间
                int newHateTime = this.currentHateTime - deltaTime;
                this.currentHateTime = Math.max(newHateTime, 0);
                if (this.currentHateTime == 0) {
                    // 仇恨消失
                    this.hateRole = null;
                }
            }
            // 2.2 尝试追踪该仇恨对象
            if (!trackHate(thisEnemy)) {
                // 已经无法找到一条到达仇恨对象的通路了, 仇恨丢失?
                // TODO: 是否还要记住这个仇恨?
                this.currentHateTime = 0;
                return;
            }
            // 2.3 尝试开火
            tryShoot();
            // 2.4 结束
            return;
        }

        // 3. 没有仇恨对象, 则尝试找一个仇恨对象
        List<ElementObj> players = AI.EM.getElementsByKey(GameElement.PLAYER);
        for (ElementObj player: players) {
            if (player instanceof Role) {
                tryHate(thisEnemy, (Role) player);
            }
        }

        // 4. 如果没有找到, 则执行 idle 行为
        idle();
    }

    /**
     * 尝试去仇恨一个对象
     * @param thisEnemy AI 控制的小怪
     * @param target 尝试仇恨的对象
     */
    private boolean tryHate(Enemy thisEnemy, Role target) {
        // 判断 target 是否在 thisEnemy 视野中
        boolean result = this.judgeInHorizon(thisEnemy, target);
        this.inHorizon = result;
        if (result) {
            // 如果是, 则设置仇恨
            this.hateRole = target;
            this.currentHateTime = this.hateTime;
        }
        return result;
    }

    /**
     * 用于判断 target 是否在 thisEnemy 的视野中
     * @param thisEnemy AI 控制的小怪
     * @param target 检测目标
     * @return true: 在视野中
     */
    private boolean judgeInHorizon(Enemy thisEnemy, Role target) {
        int xm = thisEnemy.getX();
        int ym = thisEnemy.getY();
        int xp = target.getX();
        int yp = target.getY();

        // 1. 判断视距
        int xDiff = xm - xp;
        int yDiff = ym - yp;
        int drs = xDiff*xDiff + yDiff*yDiff;
        if (drs > this.disS) {
            return false;
        }

        // 2. 判断视角
        Vector vm = AI.getDirVecByDir(thisEnemy.getFaceDir());
        Vector vp = new Vector(xp - xm, yp - ym);
        double dotValue = vm.dot(vp);
        if (dotValue < 0.0) {
            // 钝角
            return false;
        }
        double angleRCosSquare = dotValue / (double)drs;
        if (angleRCosSquare < this.angS) {
            return false;
        }

        // 3. 判断障碍
        return isExistWall(thisEnemy, target);
    }

    /**
     * 根据朝向, 获取一个预定义的方向向量(normalized)
     * @param dir 朝向
     * @return 方向向量
     */
    private static Vector getDirVecByDir(Direction dir) {
        return switch (dir.getDir()) {
            case D_N  -> V_N;
            case D_S  -> V_S;
            case D_E  -> V_E;
            case D_W  -> V_W;
            case D_NW -> V_NW;
            case D_NE -> V_NE;
            case D_SW -> V_SW;
            case D_SE -> V_SE;
            default   -> null; // Unreachable
        };
    }

    /**
     * 根据方向向量(denormalized), 获取一个朝向
     * @param dirVec 方向向量
     * @return 朝向
     */
    private static int getDirByDirVec(Vector dirVec) {
        if      (dirVec == VUN_N ) return D_N;
        else if (dirVec == VUN_S ) return D_S;
        else if (dirVec == VUN_E ) return D_E;
        else if (dirVec == VUN_W ) return D_W;
        else if (dirVec == VUN_NW) return D_NW;
        else if (dirVec == VUN_NE) return D_NE;
        else if (dirVec == VUN_SW) return D_SW;
        else if (dirVec == VUN_SE) return D_SE;
        else return 0; // Unreachable
    }

    /**
     * 用于判断视线中是否存在障碍物
     * @param thisEnemy 视线的起点
     * @param target 视线的终点
     * @return true: 存在障碍物
     */
    private boolean isExistWall(Enemy thisEnemy, Role target) {
        int xm = thisEnemy.getX();
        int ym = thisEnemy.getY();
        int xp = target.getX();
        int yp = target.getY();

        // Bresenham 直线光栅化算法
        int dx = Math.abs(xm - xp);
        int dy = Math.abs(ym - yp);
        int e  = -2 * dx;
        int y  = ym;

        if (xm < xp) {
            for (int x = xm; x <= xp; x++) {
                // TODO: check wall (x, y)
                e += 2 * dy;
                if (e > 0)   y++;
                if (e >= dx) e -= 2*dx;
            }
        } else {
            for (int x = xm; x >= xp; x--) {
                // TODO: check wall (x, y)
                e += 2 * dy;
                if (e > 0)   y++;
                if (e >= dx) e -= 2*dx;
            }
        }

        return false;
    }

    /**
     * 追踪仇恨对象
     * @param thisEnemy AI 控制的小怪
     * @return 如果能够找到路径, 则返回 true;
     */
    private boolean trackHate(Enemy thisEnemy) {
        int xm = thisEnemy.getX();
        int ym = thisEnemy.getY();
        int xp = this.hateRole.getX();
        int yp = this.hateRole.getY();

        if (this.inHorizon) {
            // 贪心
            //   *
            //  / Vp
            // o
            // 寻找和 VP 最接近的方向移动 (夹角最小)
            Vector vp = new Vector(xp - xm, yp - ym);
            double close = -1.0;
            int closePos = 0;
            for (int i = 0; i < VECTORS.length; i++) {
                double dot = vp.dot(VECTORS[i]);
                if (dot < 0.0) continue;

                double value = vp.angleCosSquare(VECTORS[i]);
                if (value > close) {
                    close = value;
                    closePos = i;
                }
            }

            AI.setDirByClosePos(thisEnemy, closePos);
            return true;
        } else {
            return aStarSearch(thisEnemy);
        }
    }

    private static void setDirByClosePos(Enemy thisEnemy, int closePos) {
        switch (closePos) {
            case 0  -> thisEnemy.setMoveDir(D_N);
            case 1  -> thisEnemy.setMoveDir(D_S);
            case 2  -> thisEnemy.setMoveDir(D_E);
            case 3  -> thisEnemy.setMoveDir(D_W);
            case 4  -> thisEnemy.setMoveDir(D_NW);
            case 5  -> thisEnemy.setMoveDir(D_NE);
            case 6  -> thisEnemy.setMoveDir(D_SW);
            case 7  -> thisEnemy.setMoveDir(D_SE);
            default -> { /* Do nothing */ }
        }
    }

    /**
     * A*寻路算法 启发式搜索
     * @param thisEnemy AI 控制的小怪
     * @return 是否找到路径
     */
    private boolean aStarSearch(Enemy thisEnemy) {
        PriorityQueue<Point> open  = new PriorityQueue<>();
        PriorityQueue<Point> close = new PriorityQueue<>();

        // 创建起点(enemyPos) 和 终点(targetPos)
        // 终点不会被加入到 open/close 中, 所以 cost 没有影响
        Point enemyPos  = new Point(thisEnemy.getX(), thisEnemy.getY(), 0.0);
        Point targetPos = new Point(this.hateRole.getX(), this.hateRole.getY(), 0.0);
        // 加入起点
        open.add(enemyPos);

        while (!open.isEmpty()) {
            Point n = open.poll();
            if (n.equals(targetPos)) {
                if (n.parent == null) {
                    // 不知道会不会出现这种情况, 还是特判一下比较好
                    return false;
                }
                // 找到了路径, 为 thisEnemy 设置移动方向
                while (n.parent.parent != null) {
                    n = n.parent;
                }
                Vector dirVec = new Vector(n.x - enemyPos.x, n.y - enemyPos.y);
                int dir = AI.getDirByDirVec(dirVec);
                thisEnemy.setMoveDir(dir);
                return true;
            }
            // 将 open 中代价最小的点 n 加入 close 中
            close.add(n);
            // 检查 n 点周围的点, 并做一些处理
            checkPointsAround(n, open, close, targetPos);
        }

        return false;
    }

    private void checkPointsAround(Point n, PriorityQueue<Point> open, PriorityQueue<Point> close, Point targetPos) {
        // 地图最外面一圈是墙, 而 n 点一定不是墙, 所以 n 点周围一定不会超限
        int xStart = n.x - 1;
        int xEnd   = n.x + 1;
        int yStart = n.y - 1;
        int yEnd   = n.y + 1;
        for (int x = xStart; x <= xEnd; x++) {
            for (int y = yStart; y <= yEnd; y++) {
                if (x == n.x && y == n.y) continue;
                // 创建新的点
                Point newPoint = new Point(x, y, 0.0);

                // TODO: 判断这个点是不是墙, 是的话就跳过

                // 判断这个点是不是 targetPos, 是的话就找到了路径, 没必要做下面的计算了
                if (newPoint.equals(targetPos)) {
                    newPoint.setParent(n);
                    open.add(n);
                    return;
                }

                // newPoint 在 close 中, 跳过
                if (close.contains(newPoint)) {
                    continue;
                }

                // 检测 newPoint 是否在 open 中, 并进行一定的处理
                if (checkPointInOpen(newPoint, open, n)) {
                    continue;
                }

                double dgCost = Point.COST_LINE;
                // 计算 newPoint 的 gCost
                int dx = Math.abs(x - n.x);
                int dy = Math.abs(y - n.y);
                if (dx + dy == 2) {
                    dgCost = Point.COST_DIAGONAL;
                }
                double gCost = n.gCost + dgCost;
                double hCost = newPoint.diagonalDistance(targetPos);
                newPoint.setGCost(gCost);
                newPoint.setHCost(hCost);
                newPoint.setParent(n);

                open.add(newPoint);
            }
        }
    }

    private boolean checkPointInOpen(Point newPoint, PriorityQueue<Point> open, Point parent) {
        double dgCost = Point.COST_LINE;
        for (Point point: open) {
            if (point.equals(newPoint)) {
                // 计算 newPoint 的 gCost
                int dx = Math.abs(newPoint.x - parent.x);
                int dy = Math.abs(newPoint.y - parent.y);
                if (dx + dy == 2) {
                    dgCost = Point.COST_DIAGONAL;
                }
                double gCost = parent.gCost + dgCost;
                // 判断是否需要更新
                if (point.gCost > gCost) {
                    // 更新 point
                    open.remove(point);
                    point.setGCost(gCost);
                    open.add(point);
                }
                return true;
            }
        }

        return false;
    }

    private void tryShoot() {

    }

    private void idle() {
        // TODO: idle
    }
}
