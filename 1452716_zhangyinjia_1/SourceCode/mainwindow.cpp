#include "mainwindow.h"
#include "ui_mainwindow.h"

/*MainWindow*/
/************************************************************************************************************/

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->graphicsView->setStyleSheet("background-color:rgb(128,128,105);");
    this->setFixedSize(1000, 600);
    //connect the button and the response function
    connect(ui->topFirst, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->topSecond, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->topThird, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->bottomFirst, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->bottomSecond, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->bottomThird, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->leftFirst, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->leftSecond, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->leftThird, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->rightFirst, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->rightSecond, &QPushButton::clicked, this, &MainWindow::ButtonClicked);
    connect(ui->rightThird, &QPushButton::clicked, this, &MainWindow::ButtonClicked);

    ui->pushButton->setStyleSheet("background-color:rgb(192,192,192);");
    ui->pushButton_2->setStyleSheet("background-color:rgb(255,255,255);");
    ui->pushButton_3->setStyleSheet("background-color:rgb(8,46,84);");
    ui->pushButton_4->setStyleSheet("background-color:rgb(227,23,13);");
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::ButtonClicked()
{
    int source;
    ThreadQueue* p;
    ThreadQueue* res = new ThreadQueue;
    res->nextThread = nullptr;
    QPushButton *buttonSrc = qobject_cast<QPushButton*>(sender());
    QString srcName = buttonSrc->objectName();
    kind Kind;
    direction Dir;
    if ("topFirst" == srcName) { Dir = TopToBottom; Kind = Ambulance; source = 2;}
    else if ("topSecond" == srcName) { Dir = TopToBottom; Kind = PoliceCar; source = 2;}
    else if ("topThird" == srcName) { Dir = TopToBottom; Kind = FireTruck; source = 2;}
    else if ("bottomFirst" == srcName) { Dir = BottomToTop; Kind = Ambulance; source = 4;}
    else if ("bottomSecond" == srcName) { Dir = BottomToTop; Kind = PoliceCar; source = 4;}
    else if ("bottomThird" == srcName) { Dir = BottomToTop; Kind = FireTruck; source = 4;}
    else if ("leftFirst" == srcName) { Dir = LeftToRight; Kind = Ambulance; source = 3;}
    else if ("leftSecond" == srcName) { Dir = LeftToRight; Kind = PoliceCar; source = 3;}
    else if ("leftThird" == srcName) { Dir = LeftToRight; Kind = FireTruck; source = 3;}
    else if ("rightFirst" == srcName) { Dir = RightToLeft; Kind = Ambulance; source = 5;}
    else if ("rightSecond" == srcName) { Dir = RightToLeft; Kind = PoliceCar; source = 5;}
    else if ("rightThird" == srcName) { Dir = RightToLeft; Kind = FireTruck; source = 5;}

    // new a Car object
    if (source == 2) { res->thread = new Car(Dir, Kind, mSpecialCarsTB); mSpecialCarsTB->addSumCars(1); }
    else if(source == 4) { res->thread = new Car(Dir, Kind, mSpecialCarsTB); mSpecialCarsBT->addSumCars(1); }
    else if(source == 3) { res->thread = new Car(Dir, Kind, mSpecialCarsLR); mSpecialCarsLR->addSumCars(1); }
    else if(source == 5) { res->thread = new Car(Dir, Kind, mSpecialCarsRL); mSpecialCarsRL->addSumCars(1); }

    res->thread->CarButton = new QPushButton("", this->ui->graphicsView);

    //set the color of the cars
    if (Kind == Ambulance) res->thread->CarButton->setStyleSheet("background-color:rgb(255,255,255);");
    else if (Kind == PoliceCar) res->thread->CarButton->setStyleSheet("background-color:rgb(8,46,84);");
    else res->thread->CarButton->setStyleSheet("background-color:rgb(227,23,13);");

    //set the properties of this new Car object
    if (TopToBottom == Dir)
        res->thread->CarButton->setGeometry(QRect(210, 101 - 22 * (mSpecialCarsTB->getSumCars() - 1), 20, 20));
    else if (BottomToTop == Dir)
        res->thread->CarButton->setGeometry(QRect(370, 351 + 22 * (mSpecialCarsBT->getSumCars() - 1), 20, 20));
    else if (LeftToRight == Dir)
        res->thread->CarButton->setGeometry(QRect(170 - 22 * (mSpecialCarsLR->getSumCars() - 1), 301, 20, 20));
    else if (RightToLeft == Dir)
        res->thread->CarButton->setGeometry(QRect(410 + 22 * (mSpecialCarsRL->getSumCars() - 1), 141, 20, 20));
    connect(res->thread, SIGNAL(sendSignalToRunAnimation(Car*)), this, SLOT(runAnimation(Car*)));
    res->thread->CarButton->show();

    //add it to SourceDir queue
    p = SourceDir[source];
    if (p == nullptr)
    {
        SourceDir[source] = res;
    }
    else
    {
        while (p->nextThread != nullptr) p = p->nextThread;
        p->nextThread = res;
    }

}

void MainWindow::createThreadQueue()
{
    //add cars to for roads
    direction Dir[4] = { TopToBottom, BottomToTop, LeftToRight, RightToLeft };

    for (int i = 0; i<4; i++)
    {
        ThreadQueue* res = new ThreadQueue;
        ThreadQueue* p;

        res->nextThread = nullptr;
        if (Dir[i] == TopToBottom || Dir[i] == BottomToTop)
        {
            /*new a car, set its text, parent and oriented location, at last show it*/
            res->thread = new Car(Dir[i], CommonCar, mThreadManagerOne);
            res->thread->CarButton = new QPushButton("", this->ui->graphicsView);
            //set color of the car button
            res->thread->CarButton->setStyleSheet("background-color:rgb(192,192,192);");
            //set position
            if (Dir[i] == TopToBottom)
                res->thread->CarButton->setGeometry(QRect(260, 101 - 22 * (mThreadManagerOne->getSumCars() / 2), 20, 20));
            else
                res->thread->CarButton->setGeometry(QRect(320, 351 + 22 * (mThreadManagerOne->getSumCars() / 2), 20, 20));
            // add one to thread manager
            mThreadManagerOne->addSumCars(1);
            res->thread->CarButton->show();
            // connect the signals and slots
            connect(res->thread, SIGNAL(sendSignalToRunAnimation(Car*)), this, SLOT(runAnimation(Car*)));
            // add it to SourceDir queue
            p = SourceDir[0];
            if (p != nullptr)
            {
                while (p->nextThread != nullptr) p = p->nextThread;
                p->nextThread = res;
            }
            else
            {
                SourceDir[0] = res;
            }

        }
        else
        {
            // the logic is the same as preceding part
            res->thread = new Car(Dir[i], CommonCar, mThreadManagerTwo);
            res->thread->CarButton = new QPushButton("", this->ui->graphicsView);
            //set color of the car button
            res->thread->CarButton->setStyleSheet("background-color:rgb(192,192,192);");
            if (Dir[i] == LeftToRight)
                res->thread->CarButton->setGeometry(QRect(170 -  22 * ( mThreadManagerTwo->getSumCars() / 2 ), 251, 20, 20));
            else
                res->thread->CarButton->setGeometry(QRect(410 +  22 * ( mThreadManagerTwo->getSumCars() / 2 ), 191, 20, 20));
            mThreadManagerTwo->addSumCars(1);
            res->thread->CarButton->show();
            connect(res->thread, SIGNAL(sendSignalToRunAnimation(Car*)), this, SLOT(runAnimation(Car*)));
            p = SourceDir[1];
            if (p != nullptr)
            {
                while (p->nextThread != nullptr) p = p->nextThread;
                p->nextThread = res;
            }
            else
            {
                SourceDir[1] = res;
            }
        }
    }

}

void MainWindow::runAttemper()
{
    runProtectedAttemper();
}

void MainWindow::runProtectedAttemper()
{
    mThreadManagerOne = new ThreadManagement(0);
    mThreadManagerTwo = new ThreadManagement(1);
    mSpecialCarsTB = new ThreadManagement(2);
    mSpecialCarsBT = new ThreadManagement(4);
    mSpecialCarsLR = new ThreadManagement(3);
    mSpecialCarsRL = new ThreadManagement(5);

    this->ui->light1->setStyleSheet("background-color:rgb(0,180,50);");
    this->ui->light3->setStyleSheet("background-color:rgb(0,180,50);");
    this->ui->light2->setStyleSheet("background-color:rgb(180,0,50);");
    this->ui->light4->setStyleSheet("background-color:rgb(180,0,50);");

    connect(mThreadManagerOne,&ThreadManagement::signalsToMoveCars, this, &MainWindow::moveCars);
    connect(mThreadManagerTwo,&ThreadManagement::signalsToMoveCars, this, &MainWindow::moveCars);

    /*create a CommonCar at each direction every 2sec*/
    connect(&timerForCar, &QTimer::timeout, this, &MainWindow::createThreadQueue);

    /*the lights change every 8sec*/
    connect(&timerForLight, &QTimer::timeout, this, [this]
    {
        tbLight = 1 - tbLight;
        rlLight = 1 - rlLight;
        changeLights();
    });



    timerForCar.start(2000);
    timerForLight.start(8000);

    ThreadManagement::schedule(mThreadManagerOne, mThreadManagerTwo,
                               mSpecialCarsTB, mSpecialCarsBT,
                               mSpecialCarsLR, mSpecialCarsRL);

}

void MainWindow::runAnimation(Car* car)
{
    /*animation code*/
    QString  strKind;
    int Kind = car->getKind();
    int Dir = car->getDir();
    switch (Kind)
    {
    case CommonCar: strKind = "CC"; break;
    case Ambulance: strKind = "AM"; break;
    case PoliceCar: strKind = "PC"; break;
    case FireTruck: strKind = "FT"; break;
    }
    QPropertyAnimation *Animation = new QPropertyAnimation(car->CarButton, "geometry");

    Animation->setDuration(1000);
    switch (Dir)
    {
    case TopToBottom:
        strKind == "CC" ? Animation->setEndValue(QRect(260, 480, 20, 20))
                        : Animation->setEndValue(QRect(210, 480, 20, 20));
        break;
    case BottomToTop:
        strKind == "CC" ? Animation->setEndValue(QRect(320, -30, 20, 20))
                        : Animation->setEndValue(QRect(370, -30, 20, 20));
        break;
    case LeftToRight:
        strKind == "CC" ? Animation->setEndValue(QRect(610, 251, 20, 20))
                        : Animation->setEndValue(QRect(610, 301, 20, 20));
        break;
    case RightToLeft:
        strKind == "CC" ? Animation->setEndValue(QRect(-30, 191, 20, 20))
                        : Animation->setEndValue(QRect(-30, 141, 20, 20));
        break;
    }
    Animation->start();

}

void MainWindow::changeLights()
{
    if (0 == tbLight)
    {
        this->ui->light1->setStyleSheet("background-color:rgb(180,0,50);");
        this->ui->light3->setStyleSheet("background-color:rgb(180,0,50);");
    }
    else
    {
        this->ui->light1->setStyleSheet("background-color:rgb(0,180,50);");
        this->ui->light3->setStyleSheet("background-color:rgb(0,180,50);");
    }

    if (0 == rlLight)
    {
        this->ui->light2->setStyleSheet("background-color:rgb(180,0,50);");
        this->ui->light4->setStyleSheet("background-color:rgb(180,0,50);");
    }
    else
    {
        this->ui->light2->setStyleSheet("background-color:rgb(0,180,50);");
        this->ui->light4->setStyleSheet("background-color:rgb(0,180,50);");
    }
}

void MainWindow::moveCars(int source, int sumcars)
{
    ThreadQueue* p = SourceDir[source];
    for(int i = 1; i <= sumcars; i++ )
    {

        QPropertyAnimation *Animation = new QPropertyAnimation(p->thread->CarButton,"geometry");
        Animation->setDuration(500);
        int fix = (i-1)/2;
        switch(p->thread->getDir())
        {
        case TopToBottom:
            Animation->setEndValue(QRect(260, 101 - 22 * fix, 20, 20));
            break;
        case BottomToTop:
            Animation->setEndValue(QRect(320, 351 + 22 * fix, 20, 20));
            break;
        case LeftToRight:
            Animation->setEndValue(QRect(170 - 22 * fix, 251, 20, 20));
            break;
        case RightToLeft:
            Animation->setEndValue(QRect(410 + 22 * fix, 191, 20, 20));
            break;
        }
        Animation->start();
        if(p!=nullptr && p->nextThread != nullptr)
            p = p->nextThread;
    }
}

/*Car*/
/***********************************************************************************************************/

Car::Car(direction Dir, kind Kind, QObject* parent) : mDir(Dir), mKind(Kind), QObject(parent) {}

Car::~Car()
{
    delete CarButton;
}

void Car::run()
{
    emit sendSignalToRunAnimation(this);
}

direction Car::getDir()
{
    return this->mDir;
}

kind Car::getKind()
{
    return mKind;
}


/*ThreadManagement*/
/***********************************************************************************************************/

ThreadManagement::ThreadManagement(int source)
{
    mSource = source;
    mSumCars = 0;
}

void ThreadManagement::addSumCars(int pa)
{
    mSumCars += pa;
}

int  ThreadManagement::getSumCars()
{
    return mSumCars;
}

int ThreadManagement::getSource()
{
    return mSource;
}

void ThreadManagement::runOfCommonCars()
{
    int minusCars = 0;
    forever{
        if (SourceDir[mSource] != nullptr)
        {
            /*P-operation*/
            mutex->lock();
            crossRoad--;
            if (crossRoad < 0)
            {
                waitThread->wait(mutex);
            }

            /*visiting critical resource*/
            ThreadQueue *p = SourceDir[mSource];
            int odd = 0;
            waitThread->wait(mutex,300);
            while (
                   /*check if the light is right*/
                   ((mSource == 0) && (tbLight == 1 && rlLight == 0))
                   ||
                   ((mSource == 1) && (rlLight == 1 && tbLight == 0))
                   )

            {
                if (p != nullptr)
                {
                    //the schdule of the cars on the same road obeys FCFS
                    p->thread->run();
                    minusCars++;
                    //move to next Car
                    SourceDir[mSource] = SourceDir[mSource]->nextThread;
                    odd++;
                    if (0 == odd % 2)
                    {
                        waitThread->wait(mutex,500);
                        odd = 0;
                    }
                }

                while (emergency <= 0 && runningEmergency%2 != mSource%2)
                {
                    waitThread->wait(mutex,80);
                }
                p = SourceDir[mSource];

            }
            mSumCars -= minusCars;
            minusCars = 0;
            if(nullptr != SourceDir[mSource])
            {
                emit signalsToMoveCars(mSource, mSumCars);
            }


            /*V-operation*/
            crossRoad++;
            mutex->unlock();
            if (crossRoad <= 0)
            {
                waitThread->wakeAll();
            }
        }

    }
}

void ThreadManagement::runOfSpecialCars()
{
    int minusCars = 0;
    forever
    {
        mutex2->lock();
        while (nullptr == SourceDir[mSource])  // no emergency cars
        {
            waitThread2->wait(mutex2,200);
        }
        /*P-Opreation*/

        emergency--;
        if (emergency < 0 && mSource%2 != runningEmergency%2)   // another road has emergency cars
        {
            waitThread2->wait(mutex2);
        }
        if(runningEmergency == -1)
            runningEmergency = mSource%2 + 2;
        else
            runningEmergency+=2;
        //block the signalsForLights
        timerForLight.blockSignals(true);


        /*visiting critical resource*/
        ThreadQueue *p = SourceDir[mSource];

        if((tbLight == 0 && mSource%2 == 0) || (tbLight == 1 && mSource%2 == 1))
            waitThread2->wait(mutex2,800);
        while (nullptr != p)
        {
            p->thread->run();
            waitThread2->wait(mutex2,800);
            SourceDir[mSource] = SourceDir[mSource]->nextThread;
            minusCars ++;
            p = SourceDir[mSource];

        }
        mSumCars -= minusCars;
        minusCars = 0;

        /*V-Operation*/
        emergency++;
        timerForLight.blockSignals(false);
        if(runningEmergency <= 3)
            runningEmergency = -1;
        else
            runningEmergency -= 2;
        mutex2->unlock();
        if (emergency <= 0 && runningEmergency == -1)
        {
            waitThread2->wakeAll();
        }

    }
}

void ThreadManagement::run()
{

    if (0 == mSource || 1 == mSource)
    {
        runOfCommonCars();
    }
    else
    {
        runOfSpecialCars();
    }

}

void ThreadManagement::schedule(ThreadManagement* tm1, ThreadManagement* tm2,
                                ThreadManagement* tm3, ThreadManagement* tm4,
                                ThreadManagement* tm5, ThreadManagement* tm6)
{
    tm1->start();
    tm2->start();
    tm3->start();
    tm4->start();
    tm5->start();
    tm6->start();
}
