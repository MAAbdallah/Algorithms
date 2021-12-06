package com.company;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Code {
    public void SJF() {
        Vector<Integer> e = new Vector<Integer>();
        Scanner sc = new Scanner(System.in);
        int np;
        System.out.println("Enter no of process");
        np = sc.nextInt();
        Vector<Node> v = new Vector<Node>();
        float AWT = 0, atat = 0;
        System.out.println("Enter Burst time for each process");
        for (int i = 0; i < np; i++) {
            System.out.println("Enter BT  & AVT for process " + (i + 1));
            Node n = new Node();
            n.bt = sc.nextInt();
            n.av = sc.nextInt();
            n.p = i + 1;
            v.add(n);
        }
        for (int i = 0; i < np - 1; i++) {
            for (int j = i + 1; j < np; j++) {
                if (v.get(i).av > v.get(j).av) {
                    Collections.swap(v, i, j);
                }
            }
        }
        int Timer = 0, sum = 0;
        for (int i = 0; i < v.size(); i++) {
            sum += v.get(i).bt;
        }
        Vector<Node> Q = new Vector<Node>();
        Vector<Node> v2 = new Vector<Node>();

        for (int i = 0; i < v.size(); i++) {
            Node n = new Node();
            n.bt = v.get(i).bt;
            n.p = v.get(i).p;
            n.av = v.get(i).av;
            v2.add(n);
        }
        int counter = 0;
        boolean check = false;
        while (v.size() > 0) {
            counter++;
            if (v.get(0).av == Timer) {
                Q.add(v.get(0));
                v.remove(0);

//sorting on the basis of priority
                for (int i = 0; i < Q.size() - 1; i++) {
                    for (int j = i + 1; j < Q.size(); j++) {
                        if (Q.get(i).bt > Q.get(j).bt) {
                            Collections.swap(Q, i, j);
                        }
                    }
                }
                for (int i = 0; i < e.size(); i++) {
                    if (Q.get(0).p == e.get(e.size() - 1)) {
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    e.add(Q.get(0).p);
                }
                check = false;
                Q.get(0).bt--;
                if (Q.get(0).bt == 0) {
                    int index = -1;
                    for (int j = 0; j < v2.size(); j++) {
                        if (Q.get(0).av == v2.get(j).av) {
                            index = j;
                            break;
                        }
                    }
                    v2.get(index).w = Timer + 1 - v2.get(index).bt - v2.get(index).av;
                    Q.remove(0);
                }
                Timer++;
            } else {
                Q.get(0).bt--;
                if (Q.get(0).bt == 0) {
                    int index = -1;
                    for (int j = 0; j < v2.size(); j++) {
                        if (Q.get(0).av == v2.get(j).av) {
                            index = j;
                            break;
                        }
                    }

                    for (int i = 0; i < e.size(); i++) {
                        if (Q.get(0).p == e.get(e.size() - 1)) {
                            check = true;
                            break;
                        }
                    }
                    if (check == false) {
                        e.add(Q.get(0).p);
                    }
                    check = false;
                    v2.get(index).w = Timer + 1 - v2.get(index).bt - v2.get(index).av;
                    Q.remove(0);
                }

                Timer++;
            }
        }
        for (; counter < sum && Q.size() > 0; counter++) {
            Q.get(0).bt--;
            if (Q.get(0).bt == 0) {
                int index = -1;
                for (int j = 0; j < v2.size(); j++) {
                    if (Q.get(0).av == v2.get(j).av) {
                        index = j;
                        break;
                    }
                }

                for (int i = 0; i < e.size(); i++) {
                    if (Q.get(0).p == e.get(e.size() - 1)) {
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    e.add(Q.get(0).p);
                }
                check = false;
                v2.get(index).w = Timer + 1 - v2.get(index).bt - v2.get(index).av;
                Q.remove(0);

            }

            Timer++;
        }
        for (int i = 0; i < np; i++) {
            v2.get(i).t = v2.get(i).bt + v2.get(i).w;
        }
        System.out.print("\n\nProcess \t arrival Time \t Burst Time \t Wait Time \t Turn Around Time        \n");
        for (int i = 0; i < np; i++) {
            System.out.print("\n   " + v2.get(i).p + "\t\t\t   " + v2.get(i).av + "\t\t\t   " + v2.get(i).bt + "\t\t\t     " + v2.get(i).w + "\t\t\t     " + v2.get(i).t + "\t\t\t     " + "\n");
        }
        /*System.out.println("  PROCESS   BT      WT      TAT   arrival  ");
        for ( int i = 0; i < np; i++) {
            System.out.println("     P" + i+1 + "     " + v2.get(i).bt + "     " + v2.get(i).w + "     " + v2.get(i).t+"     " + v2.get(i).av);
        }*/
        for (int j = 0; j < np; j++) {
            AWT += v2.get(j).w;
            atat += v2.get(j).t;
        }

        AWT = AWT / np;
        atat = atat / np;
        System.out.println("***********************************************");
        System.out.println("Avarege Waiting Time = " + AWT);
        System.out.println("Avarege total around Time = " + atat);
        for (int i = 0; i < e.size(); i++) {
            System.out.print("process " + e.get(i) + " ");
        }

    }

    public void RR() {
        Vector<Integer> e = new Vector<Integer>();

        Scanner sc = new Scanner(System.in);
        int i, j, k, q, sum = 0;
        System.out.println("Enter number of process:");
        int np = sc.nextInt();
        int temp[] = new int[np];
        Vector<Node> v = new Vector<Node>();
        System.out.println("Enter brust Time:");
        for (i = 0; i < np; i++) {
            System.out.println("Enter brust Time for " + (i + 1));
            Node n = new Node();
            n.bt = sc.nextInt();
            n.p = i + 1;
            v.add(n);
        }
        System.out.println("Enter Time quantum:");
        q = sc.nextInt();
        for (i = 0; i < np; i++) {
            temp[i] = v.get(i).bt;
        }
        for (k = 0; k < np; k++) {
            sum = sum + v.get(k).bt;
        }
        while (sum > 0) {
            for (i = 0; i < np; i++) {
                if (v.get(i).bt != 0) {
                    e.add(v.get(i).p);
                }
                if (v.get(i).bt > q) {
                    v.get(i).bt -= q;
                    for (j = 0; j < np; j++) {
                        if ((j != i) && (v.get(j).bt != 0)) {
                            v.get(j).w += q;
                        }
                    }
                } else {
                    for (j = 0; j < np; j++) {
                        if ((j != i) && (v.get(j).bt != 0)) {
                            v.get(j).w += v.get(i).bt;
                        }
                    }
                    v.get(i).bt = 0;
                }
            }
            sum--;
        }
        for (i = 0; i < np; i++) {
            v.get(i).t = v.get(i).w + temp[i];
        }
        System.out.println("process\t\tBT\tWT\tTAT");
        for (i = 0; i < np; i++) {
            System.out.println("process" + (i + 1) + "\t" + temp[i] + "\t" + v.get(i).w + "\t" + v.get(i).t);
        }
        System.out.println("");
        float avg_wt = 0;
        float avg_tat = 0;
        for (j = 0; j < np; j++) {
            avg_wt += v.get(j).w;
        }
        for (j = 0; j < np; j++) {
            avg_tat += v.get(j).t;
        }
        System.out.println("average waiting time " + (avg_wt / np) + "\nAverage turn around time" + (avg_tat / np));
        System.out.println("");
        for (i = 0; i < e.size(); i++) {
            System.out.print("process " + e.get(i) + " ");
        }
    }

    public void priority() {
        Scanner s = new Scanner(System.in);
        Vector<Integer> e = new Vector<Integer>();

        int np, i = 0;
        float awt = 0, atat = 0;

        System.out.print("Enter the number of process : ");
        np = s.nextInt();
        System.out.print("\n\t Enter burst time : time priorities : time arrival  \n");
        Vector<Node> v = new Vector<Node>();
        for (i = 0; i < np; i++) {
            System.out.print("\nProcess[" + (i + 1) + "]:");
            Node n = new Node();
            n.bt = s.nextInt();
            n.pp = s.nextInt();
            n.av = s.nextInt();
            n.p = i + 1;
            v.add(n);
        }

//sorting on the basis of av
        for (i = 0; i < np - 1; i++) {
            for (int j = i + 1; j < np; j++) {
                if (v.get(i).av > v.get(j).av) {
                    Collections.swap(v, i, j);
                }
            }
        }

        int sum = 0;
        Vector<Node> Q = new Vector<Node>();
        int Timer = 0;
        Vector<Node> v2 = new Vector<Node>();
        for (i = 0; i < v.size(); i++) {
            Node n = new Node();
            n.bt = v.get(i).bt;
            n.p = v.get(i).p;
            n.pp = v.get(i).pp;
            n.av = v.get(i).av;
            v2.add(n);
        }

        for (i = 0; i < v.size(); i++) {
            sum += v.get(i).bt;
        }
        int counter = 0;
        boolean check = false;
        while (v.size() > 0) {
            counter++;
            if (v.get(0).av == Timer) {
                Q.add(v.get(0));
                v.remove(0);

//sorting on the basis of priority
                for (i = 0; i < Q.size() - 1; i++) {
                    for (int j = i + 1; j < Q.size(); j++) {
                        if (Q.get(i).pp > Q.get(j).pp) {
                            Collections.swap(Q, i, j);
                        }
                    }
                }

                /*for ( i=1;i<Q.size();i++)
                {
                    Q.get(i).pp--;
                }*/
                for (i = 0; i < e.size(); i++) {
                    if (Q.get(0).p == e.get(e.size() - 1)) {
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    e.add(Q.get(0).p);
                }
                check = false;
                Q.get(0).bt--;
                if (Q.get(0).bt == 0) {
                    int index = -1;
                    for (int j = 0; j < v2.size(); j++) {
                        if (Q.get(0).pp == v2.get(j).pp) {
                            index = j;
                            break;
                        }
                    }
                    v2.get(index).w = Timer + 1 - v2.get(index).bt - v2.get(index).av;
                    Q.remove(0);
                }
                Timer++;
            } else {
                /*for ( i=1;i<Q.size();i++)
                {
                    Q.get(i).pp--;
                }*/
                Q.get(0).bt--;
                if (Q.get(0).bt == 0) {

                    int index = -1;
                    for (int j = 0; j < v2.size(); j++) {
                        if (Q.get(0).pp == v2.get(j).pp) {
                            index = j;
                            break;
                        }
                    }

                    for (i = 0; i < e.size(); i++) {
                        if (Q.get(0).p == e.get(e.size() - 1)) {
                            check = true;
                            break;
                        }
                    }
                    if (check == false) {
                        e.add(Q.get(0).p);
                    }
                    check = false;
                    v2.get(index).w = Timer + 1 - v2.get(index).bt - v2.get(index).av;
                    Q.remove(0);
                }

                Timer++;
            }
        }
        for (; counter < sum && Q.size() > 0; counter++) {
            Q.get(0).bt--;
            /*for ( i=1;i<Q.size();i++)
            {
                Q.get(i).pp--;
            }*/
            if (Q.get(0).bt == 0) {

                int index = -1;
                for (int j = 0; j < v2.size(); j++) {
                    if (Q.get(0).av == v2.get(j).av) {
                        index = j;
                        break;
                    }
                }

                for (i = 0; i < e.size(); i++) {
                    if (Q.get(0).p == e.get(e.size() - 1)) {
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    e.add(Q.get(0).p);
                }
                check = false;
                v2.get(index).w = Timer + 1 - v2.get(index).bt - v2.get(index).av;
                Q.remove(0);
            }
            Timer++;
        }
        for (i = 0; i < np; i++) {
            v2.get(i).t = v2.get(i).bt + v2.get(i).w;
        }

        for (i = 0; i < np; i++) {
            awt += v2.get(i).w;
            atat += v2.get(i).t;
        }

//Displaying the process

        System.out.print("\n\nProcess \t arrival Time \t Burst Time \t Wait Time \t Turn Around Time       Priority \n");
        for (i = 0; i < np; i++) {
            System.out.print("\n   " + v2.get(i).p + "\t\t\t   " + v2.get(i).av + "\t\t\t   " + v2.get(i).bt + "\t\t\t     " + v2.get(i).w + "\t\t\t     " + v2.get(i).t + "\t\t\t     " + v2.get(i).pp + "\n");
        }
        System.out.print("\n Average Wait Time : " + awt / np);
        System.out.println("\n Average Turn Around Time : " + atat / np);
        for (i = 0; i < e.size(); i++) {
            System.out.print("process " + e.get(i) + " ");
        }
    }


    public Vector<Node> search(int t, Vector<Node> v1) {
        Vector<Node> temp = new Vector<Node>();
        for (int i = 0; i < v1.size(); i++) {
            Node n = new Node();
            if (v1.get(i).av <= t) {
                n.p = v1.get(i).p;
                n.Q = v1.get(i).Q;
                n.av = v1.get(i).av;
                n.bt = v1.get(i).bt;
                temp.add(n);
            }
        }
        return temp;
    }

    public void AG() {
        Vector<Integer> e = new Vector<Integer>();
        Scanner sc = new Scanner(System.in);
        int np;
        System.out.println("Enter no of process");
        np = sc.nextInt();
        Vector<Node> v = new Vector<Node>();
        float AWT = 0, atat = 0;
        System.out.println("Enter Burst time for each process");
        for (int i = 0; i < np; i++) {
            System.out.println("Enter BT  & AVT for process & Q " + (i + 1));
            Node n = new Node();
            n.bt = sc.nextInt();
            n.av = sc.nextInt();
            n.Q = sc.nextInt();
            n.p = i + 1;
            v.add(n);
        }
        for (int i = 0; i < np - 1; i++) {
            for (int j = i + 1; j < np; j++) {
                if (v.get(i).av > v.get(j).av) {
                    Collections.swap(v, i, j);
                }
            }
        }
        int Timer = 0, sum = 0;
        for (int i = 0; i < v.size(); i++) {
            sum += v.get(i).bt;
        }
        Vector<Node> Q = new Vector<Node>();
        Vector<Node> v2 = new Vector<Node>();
        for (int i = 0; i < v.size(); i++) {
            Node n = new Node();
            n.bt = v.get(i).bt;
            n.p = v.get(i).p;
            n.av = v.get(i).av;
            n.Q = v.get(i).Q;
            v2.add(n);
        }
        int counter = 0;
        boolean check = false;
        Node n = new Node();
        while (v.size() > 0) {
            if (v.get(0).av == Timer) {
                Q.add(v.get(0));
                v.remove(0);
                //sorting on the basis of brust
                for (int i = 0; i < Q.size() - 1; i++) {
                    for (int j = i + 1; j < Q.size(); j++) {
                        if (Q.get(i).bt > Q.get(j).bt) {
                            Collections.swap(Q, i, j);
                        }
                    }
                }
                /*for(int i =0;i<e.size();i++)
                {
                    if(Q.get(0).p==e.get(e.size()-1))
                    {
                        check = true ;
                        break;
                    }
                }
                if (check==false)
                {
                    e.add(Q.get(0).p);
                }
                check=false;*/
                n = Q.get(0);
                System.out.println("P : "+n.p+" start : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                int btTemp = n.bt;
                Q.remove(0);
                n.bt -= Math.ceil(0.5 * n.Q);
                //System.out.println("before calc Quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q+" Time : "+Timer);
                n.checkQ += Math.ceil(0.5 * n.Q);
                if (n.bt <= 0) {
                    int index = -1;
                    for (int j = 0; j < v2.size(); j++) {
                        if (n.av == v2.get(j).av) {
                            index = j;
                            break;
                        }
                    }
                    Timer += btTemp;
                    v2.get(index).w = Timer  - v2.get(index).bt - v2.get(index).av;
                    n.bt = 0;
                    System.out.println("P : "+n.p+" finished : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                    n = null;
                } else {
                    Timer += Math.ceil(0.5 * n.Q);
                }
                if (n != null) {
                    Vector<Node> temp = search(Timer, v);
                    for (int i = 0; i < temp.size(); i++) {
                        Node node = new Node();
                    /*node.bt    =  temp.get(i).bt;
                    node.p     =  temp.get(i).p;
                    node.av    =  temp.get(i).av;
                    node.Q     =  temp.get(i).Q;*/
                        node = temp.get(i);
                        Q.add(node);
                    }
                    for (int i = 0; i < temp.size(); i++) {
                        for (int j = 0; j < v.size(); j++) {
                            if (temp.get(i).av == v.get(j).av) {
                                v.remove(j);
                                break;
                            }
                        }
                    }
                    //sorting on the basis of brust
                    for (int i = 0; i < Q.size() - 1; i++) {
                        for (int j = i + 1; j < Q.size(); j++) {
                            if (Q.get(i).bt > Q.get(j).bt) {
                                Collections.swap(Q, i, j);
                            }
                        }
                    }
                    int reming = (int) (n.Q - Math.ceil(0.5 * n.Q));
                    if (Q.size() > 0) {
                        Node NewNode = new Node();
                        NewNode = Q.get(0);
                        Q.remove(0);
                        //case 2
                        if (NewNode.bt < n.bt&&n.checkQ>=Math.ceil(0.5 * n.Q)) {
                            n.Q += reming;
                            n.checkQ = 0;
                            System.out.println("P : "+n.p+" case 2 after calc Quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                            Q.add(n);
                            n = NewNode;
                            System.out.println("P : "+n.p+" new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                        }
                        // case 1
                        else if (n.checkQ >= n.Q) {
                            Node temp1 = NewNode;
                            n.Q += 1;
                            NewNode = n;
                            NewNode.checkQ = 0;
                            System.out.println("P : "+n.p+" case 2 after calc Quantum : " + " brust : " + NewNode.bt + " arrival : " + NewNode.av + " Q : " + NewNode.Q + " Time : " + Timer);
                            n = temp1;
                            System.out.println("P : "+n.p+" new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                            Q.add(NewNode);
                        } else {
                            Q.add(NewNode);
                            //sorting on the basis of priority
                            for (int i = 0; i < Q.size() - 1; i++) {
                                for (int j = i + 1; j < Q.size(); j++) {
                                    if (Q.get(i).bt > Q.get(j).bt) {
                                        Collections.swap(Q, i, j);
                                    }
                                }
                            }
                        }
                        //System.out.println("onTime : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q);
                    }
                } else {
                    Timer++;
                }
            } else if (n.bt > 0) {
                int btTemp = n.bt;
                System.out.println("P : "+n.p+" before calc Quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                n.bt -= Math.ceil(0.5 * n.Q);
                //System.out.println("after calc Quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q+" Time : "+Timer);
                n.checkQ += Math.ceil(0.5 * n.Q);
                if (n.bt <= 0) {
                    int index = -1;
                    for (int j = 0; j < v2.size(); j++) {
                        if (n.av == v2.get(j).av) {
                            index = j;
                            break;
                        }
                    }
                    Timer += btTemp;
                    v2.get(index).w = Timer - v2.get(index).bt - v2.get(index).av;
                    n.bt = 0;
                    System.out.println("P : "+n.p+" Node finish : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                    n = null;
                } else {
                    Timer += Math.ceil(0.5 * n.Q);
                }
                Vector<Node> temp = search(Timer, v);
                for (int i = 0; i < temp.size(); i++) {
                    Node node = new Node();
                    /*node.bt    =  temp.get(i).bt;
                    node.p     =  temp.get(i).p;
                    node.av    =  temp.get(i).av;
                    node.Q     =  temp.get(i).Q;*/
                    node = temp.get(i);
                    Q.add(node);
                }
                for (int i = 0; i < temp.size(); i++) {
                    for (int j = 0; j < v.size(); j++) {
                        if (temp.get(i).av == v.get(j).av) {
                            v.remove(j);
                            break;
                        }
                    }
                }
                //sorting on the basis of priority
                for (int i = 0; i < Q.size() - 1; i++) {
                    for (int j = i + 1; j < Q.size(); j++) {
                        if (Q.get(i).bt > Q.get(j).bt) {
                            Collections.swap(Q, i, j);
                        }
                    }
                }
                if (n != null && Q.size() > 0) {
                    int reming = (int) (n.Q - Math.ceil(0.5 * n.Q));
                    Node NewNode = Q.get(0);
                    Q.remove(0);
                    if (NewNode.bt < n.bt&&n.checkQ>=Math.ceil(0.5 * n.Q)) {
                        n.Q += reming;
                        n.checkQ = 0;
                        System.out.println("P : "+n.p+" case 2 after calc Quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                        Q.add(n);
                        n = NewNode;
                        System.out.println("P : "+n.p+" new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                    } else if (n.checkQ >= n.Q) {
                        Node temp1 = NewNode;
                        n.Q += 1;
                        NewNode = n;
                        NewNode.checkQ = 0;
                        System.out.println("P : "+n.p+" case 2 after calc Quantum : " + " brust : " + NewNode.bt + " arrival : " + NewNode.av + " Q : " + NewNode.Q + " Time : " + Timer);
                        n = temp1;
                        System.out.println("P : "+n.p+" new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                        Q.add(NewNode);
                    } else {
                        Q.add(NewNode);
                        //sorting on the basis of priority
                        for (int i = 0; i < Q.size() - 1; i++) {
                            for (int j = i + 1; j < Q.size(); j++) {
                                if (Q.get(i).bt > Q.get(j).bt) {
                                    Collections.swap(Q, i, j);
                                }
                            }
                        }
                    }
                    //System.out.println("else if bt>0&qSize>0 : "+" brust : "+n.bt+" arrival : "+n.av+" Q : "+n.Q+" Time : "+Timer);
                } else if (n != null && Q.size() <= 0) {
                    System.out.println("case 3 , continue ");
                    continue;
                } else if (n == null && Q.size() > 0) {
                    n = Q.get(0);
                    Q.remove(0);
                    System.out.println("P : "+n.p+" new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                } else {
                    Timer++;
                }
            } else {
                Timer++;
            }
        }
        for (; Timer < sum; ) {
            if (n == null) {
                n = Q.get(0);
                System.out.println("P : "+n.p+" new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                int btTemp = n.bt;
                Q.remove(0);
                n.bt -= Math.ceil(0.5 * n.Q);
                //System.out.println("before calc Quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q+" Time : "+Timer);
                n.checkQ += Math.ceil(0.5 * n.Q);
                if (n.bt <= 0) {
                    int index = -1;
                    for (int j = 0; j < v2.size(); j++) {
                        if (n.av == v2.get(j).av) {
                            index = j;
                            break;
                        }
                    }

                /*for( int i =0;i<e.size();i++)
                {
                    if(Q.get(0).p==e.get(e.size()-1))
                    {
                        check = true ;
                        break;
                    }
                }
                if (check==false)
                {
                    e.add(Q.get(0).p);
                }
                check=false;*/
                    Timer += btTemp;
                    v2.get(index).w = Timer  - v2.get(index).bt - v2.get(index).av;
                    n.bt = 0;
                    System.out.println("P : "+n.p+" finished : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                    n = null;
                } else {
                    Timer += Math.ceil(0.5 * n.Q);
                }
                if (n != null && Q.size() > 0) {
                    int reming = (int) (n.Q - Math.ceil(0.5 * n.Q));
                    Node NewNode = Q.get(0);
                    Q.remove(0);
                    if (NewNode.bt < n.bt&&n.checkQ>=Math.ceil(0.5 * n.Q)) {
                        n.Q += reming;
                        n.checkQ = 0;
                        System.out.println("P : "+n.p+" case 2 after calc quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                        Q.add(n);
                        n = NewNode;
                        System.out.println("P : "+n.p+" new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                    } else if (n.checkQ >= n.Q) {
                        Node temp1 = NewNode;
                        n.Q += 1;
                        NewNode = n;
                        NewNode.checkQ = 0;
                        System.out.println("P : "+n.p+" case 1 after calc quantum : " + " brust : " + NewNode.bt + " arrival : " + NewNode.av + " Q : " + NewNode.Q + " Time : " + Timer);
                        n = temp1;
                        System.out.println("P : "+n.p+"new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                        Q.add(NewNode);
                        //sorting on the basis of brust
                        for (int i = 0; i < Q.size() - 1; i++) {
                            for (int j = i + 1; j < Q.size(); j++) {
                                if (Q.get(i).bt > Q.get(j).bt) {
                                    Collections.swap(Q, i, j);
                                }
                            }
                        }
                    } else {
                        Q.add(NewNode);
                        //sorting on the basis of priority
                        for (int i = 0; i < Q.size() - 1; i++) {
                            for (int j = i + 1; j < Q.size(); j++) {
                                if (Q.get(i).bt > Q.get(j).bt) {
                                    Collections.swap(Q, i, j);
                                }
                            }
                        }
                    }
                    //System.out.println("offTime : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q);
                }
            } else if (n != null) {
                int btTemp = n.bt;
                System.out.println("P : "+n.p+" Node is continue : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                n.bt -= Math.ceil(0.5 * n.Q);
                //System.out.println("  before calc quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q+" Time : "+Timer);
                n.checkQ += Math.ceil(0.5 * n.Q);
                if (n.bt <= 0) {
                    int index = -1;
                    for (int j = 0; j < v2.size(); j++) {
                        if (n.av == v2.get(j).av) {
                            index = j;
                            break;
                        }
                    }

                /*for( int i =0;i<e.size();i++)
                {
                    if(Q.get(0).p==e.get(e.size()-1))
                    {
                        check = true ;
                        break;
                    }
                }
                if (check==false)
                {
                    e.add(Q.get(0).p);
                }
                check=false;*/
                    Timer += btTemp;
                    v2.get(index).w = Timer  - v2.get(index).bt - v2.get(index).av;
                    n.bt = 0;
                    System.out.println("P : "+n.p+" finished : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                    n = null;
                } else {
                    Timer += Math.ceil(0.5 * n.Q);
                }
                if (n != null && Q.size() > 0) {
                    int reming = (int) (n.Q - Math.ceil(0.5 * n.Q));
                    Node NewNode = Q.get(0);
                    Q.remove(0);
                    if (NewNode.bt < n.bt&&n.checkQ>=Math.ceil(0.5 * n.Q)) {
                        n.Q += reming;
                        n.checkQ = 0;
                        System.out.println("P : "+n.p+" after calc Quantum : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                        Q.add(n);
                        n = NewNode;
                        System.out.println("P : "+n.p+" new Node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                    } else if (n.checkQ >= n.Q) {
                        Node temp1 = NewNode;
                        n.Q += 1;
                        NewNode = n;
                        NewNode.checkQ = 0;
                        System.out.println("P : "+n.p+" after calc Quantum : " + " brust : " + NewNode.bt + " arrival : " + NewNode.av + " Q : " + NewNode.Q + " Time : " + Timer);
                        n = temp1;
                        System.out.println("P : "+n.p+" new node is starting : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q + " Time : " + Timer);
                        Q.add(NewNode);
                        //sorting on the basis of brust
                        for (int i = 0; i < Q.size() - 1; i++) {
                            for (int j = i + 1; j < Q.size(); j++) {
                                if (Q.get(i).bt > Q.get(j).bt) {
                                    Collections.swap(Q, i, j);
                                }
                            }
                        }
                    } else {
                        Q.add(NewNode);
                        //sorting on the basis of priority
                        for (int i = 0; i < Q.size() - 1; i++) {
                            for (int j = i + 1; j < Q.size(); j++) {
                                if (Q.get(i).bt > Q.get(j).bt) {
                                    Collections.swap(Q, i, j);
                                }
                            }
                        }
                    }
                    //System.out.println("offTime : " + " brust : " + n.bt + " arrival : " + n.av + " Q : " + n.Q);
                }
            }
        }
        for (int i = 0; i < np; i++) {
            v2.get(i).t = v2.get(i).bt + v2.get(i).w;
        }
        System.out.print("\n\nProcess \t arrival Time \t Burst Time \t Wait Time \t Turn Around Time        \n");
        for (int i = 0; i < np; i++) {
            System.out.print("\n   " + v2.get(i).p + "\t\t\t   " + v2.get(i).av + "\t\t\t   " + v2.get(i).bt + "\t\t\t     " + v2.get(i).w + "\t\t\t     " + v2.get(i).t + "\t\t\t     " + "\n");
        }
        /*System.out.println("  PROCESS   BT      WT      TAT   arrival  ");
        for ( int i = 0; i < np; i++) {
            System.out.println("     P" + i+1 + "     " + v2.get(i).bt + "     " + v2.get(i).w + "     " + v2.get(i).t+"     " + v2.get(i).av);
        }*/
        for (int j = 0; j < np; j++) {
            AWT += v2.get(j).w;
            atat += v2.get(j).t;
        }

        AWT = AWT / np;
        atat = atat / np;
        System.out.println("***********************************************");
        System.out.println("Avarege Waiting Time = " + AWT);
        System.out.println("Avarege total around Time = " + atat);
        /*for (int i=0;i<e.size();i++)
        {
            System.out.print("process "+ e.get(i)+" ");
        }*/

    }
}