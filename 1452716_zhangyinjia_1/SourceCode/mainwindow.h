#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include<QThread>
#include<QTimer>
#include<QMutex>
#include<QWaitCondition>
#include<QPropertyAnimation>
#include<QSequentialAnimationGroup>
#include<QPushButton>

namespace Ui {
class MainWindow;

}
class Car;
class ThreadManagement;



/*defination of some datatype*/
/**********************************************************************************************************/
/*define the node to control the Cars*/
struct ThreadQueue
{
    Car* thread;
    ThreadQueue *nextThread;
};

/*define the directions of cars*/
enum direction
{
    TopToBottom,
    BottomToTop,
    LeftToRight,
    RightToLeft
};

/*define the kinds of cars*/
enum kind
{
    CommonCar,
    Ambulance,
    PoliceCar,
    FireTruck
};

/*control the synchronism of running index of four directions*/
struct runIndex
{
    int tbRun = 0;
    int btRun = 0;
    int lrRun = 0;
    int rlRun = 0;
};
static runIndex IndexOfRun;


/*declaration of some globle variables*/
/*********************************************************************************************************/
/*treat the crossroad as a source, and it is mutual, 1 means it's available*/
static int crossRoad = 1;

/*only allow emergency cars running on one direction*/
static int emergency = 1;
static int runningEmergency = -1;

/*top-bottom lights, 0 - red, 1 - green*/
static int tbLight = 1;

/*left-right lights, 0 - red, 1 - green*/
static int rlLight = 0;

/*timer to add common cars*/
static QTimer timerForCar;

/*timer to change the lights*/
static QTimer timerForLight;

/* [0]-top/bottom, [1]-left/right,
 * [2]-top to bottom special cars, [3]-left to right special cars
   [4]-bottom to top special cars, [5]-right to left special cars*/
static ThreadQueue* SourceDir[6] = { nullptr, nullptr, nullptr, nullptr, nullptr,nullptr };

/* rubbish link queue, in order to recycle the space*/
static QWaitCondition* waitThread = new QWaitCondition;
static QWaitCondition* waitThread2 = new QWaitCondition;

/*control the mutex of common cars and special cars*/
static QMutex* mutex = new QMutex;
static QMutex* mutex2 = new QMutex;


/*defination of class*/
/********************************************************************************************************/
/*Main windows*/
class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    /*connect buttons' signals and reaction function*/
    explicit MainWindow(QWidget *parent = 0);

    ~MainWindow();

    /*public interface of run attemper*/
    void runAttemper();

    /*change lights per 8 sec*/
    void changeLights();
protected slots:
    /*reaction function to click button*/
    void ButtonClicked();

    /*add cars to four directions per 8 sec*/
    void createThreadQueue();
public slots:
    /*the animation of cars*/
    void runAnimation(Car* car);

    /*move the cars to the crossroad*/
    void moveCars(int source, int sumcars);

protected:
    /*the real attemper to schedule the road thread*/
    void runProtectedAttemper();


private:
    /*pointer of the ui*/
    Ui::MainWindow *ui;

    /*the thread to control the common cars*/
    ThreadManagement * mThreadManagerOne;
    ThreadManagement * mThreadManagerTwo;

    /*the thread to control the special cars*/
    ThreadManagement * mSpecialCarsTB;
    ThreadManagement * mSpecialCarsBT;
    ThreadManagement * mSpecialCarsLR;
    ThreadManagement * mSpecialCarsRL;
};


/*Car class to define and control the cars*/
class Car : public QObject
{
    Q_OBJECT

public:

    /*the Car Image(use button)*/
    QPushButton* CarButton;
    /*constructor and distructor*/
    Car(direction Dir, kind Kind, QObject* parent);
    ~Car();

    /*get information*/
    direction getDir();
    kind      getKind();

    /*implement the animation on the MainWindow*/
    void run();
signals:
    /*signals*/
    void sendSignalToRunAnimation(Car* car);
private:
    /*record the direction of this car*/
    direction mDir;

    /*record the kind of this car*/
    kind mKind;


};


/*To control the cars*/
class ThreadManagement : public QThread
{
    Q_OBJECT

public:
    ThreadManagement(int source);
    /*run all the thread*/
    static void schedule(ThreadManagement* tm1, ThreadManagement* tm2,
                         ThreadManagement* tm3, ThreadManagement* tm4,
                         ThreadManagement* tm5, ThreadManagement* tm6);

    /*main algorithm to schedule the thread*/
    void run();

    /*add pa to mSumCars*/
    void addSumCars(int pa);

    /*get mSumCars*/
    int getSumCars();

    /*get mSource*/
    int getSource();

signals:

    /*signals*/
    void signalsToMoveCars(int source, int sumcars);

private:

	void runOfCommonCars();
	void runOfSpecialCars();
    /*record the direction the cars from*/
    int mSource;

    /*record the sum of the cars*/
    int mSumCars;
};


#endif // MAINWINDOW_H
