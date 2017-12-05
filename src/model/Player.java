/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author augusto.pacher, giliard.beda
 */
public class Player {

    private Room currentRoom;
    private Stack<Room> previousRooms;
    private final ArrayList<Item> mochila;
    private int maxWeight = 50;
    private boolean biscoito = true;

    public Player() {
        previousRooms = new Stack<>();
        mochila = new ArrayList<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Stack<Room> getPreviousRooms() {
        return previousRooms;
    }

    public void setPreviousRooms(Stack<Room> previousRooms) {
        this.previousRooms = previousRooms;
    }

    public void eat() {
        System.out.println("Você comeu e agora não está mais com fome.");
    }

    public void comerBiscoito() {
        boolean verifica = false;
        String str = "biscoito";
        for (Item i : mochila) {
            if (str.equals(i.getNome())) {
                verifica = true;
                mochila.remove(i);
                break;
            }
        }

        if (verifica && this.biscoito) {
            this.maxWeight += 20;
            this.biscoito = false;
            System.out.println("Agora sua mochila comporta mais 20 de peso!");
        } else {
            System.out.println("Você não possui um biscoito ou já comeu o biscoito!");
        }
    }

    public void addItem() {
        if (getCurrentRoom().getItem() == null) {
            System.out.println("Esta sala não possui item!");
        } else {
            int pesoMochila = 0;
            pesoMochila = mochila.stream().map((i) -> i.getWeight()).reduce(pesoMochila, Integer::sum);

            if ((getCurrentRoom().getItem().getWeight() + pesoMochila) > maxWeight) {
                System.out.println("Mochila não aguenta tanto peso!");
            } else {
                mochila.add(getCurrentRoom().getItem());
                System.out.println(getCurrentRoom().getItem().getNome() + " adicionado a mochila!");
                getCurrentRoom().setItem(null);
            }
        }
    }

    public void listItems() {
        if (mochila.isEmpty()) {
            System.out.println("A mochila está vazia!");
        } else {
            System.out.println("Itens da mochila: ");
            int count = 0;
            count = mochila.stream().map((i) -> {
                System.out.println(i.getNome() + ": " + i.getWeight());
                return i;
            }).map(Item::getWeight).reduce(count, Integer::sum);
            System.out.println("Peso total da mochila: " + count + "/" + maxWeight);
        }
    }

    public void removeItem(Command comando) {
        int count = mochila.size();
        if (mochila.isEmpty()) {
            System.out.println("A mochila está vazia!");
        } else {
            for (Item i : mochila) {
                if (i.getNome().equals(comando.getSecondWord())) {
                    if (getCurrentRoom().getItem() == null) {
                        System.out.println(i.getNome() + " dropado na sala!");
                        getCurrentRoom().setItem(i);
                        mochila.remove(i);
                    } else {
                        System.out.println(i.getNome() + " deletado do mapa! (sala ja contem um item)");
                        mochila.remove(i);
                    }
                    break;
                }
            }
            if (count == mochila.size()) {
                System.out.println("Este item não existe na mchila!");
            }
        }
    }
}
