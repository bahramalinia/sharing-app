package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList; // import the ArrayList class

public class ContactList {
    private ArrayList<Contact> contacts;
    private String FILENAME;

    public ContactList () {
        this.contacts = new ArrayList<Contact>();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Contact> getContacts() {
        return this.contacts;
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> userNames = new ArrayList<String>();
        for (Integer i=0; i < this.contacts.size(); ++i) {
            userNames.add(contacts.get(i).getUsername());
        }
        return userNames;
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public void deleteContact(Contact contact) {
        if (this.contacts.contains(contact)) {
            this.contacts.remove(contact);
        }
    }

    public Contact getContact(Integer index) {
        return this.contacts.get(index);
    }

    public Integer getSize() {
        return this.contacts.size();
    }

    public Integer getIndex(Contact contact) {
        return this.contacts.indexOf(contact);
    }

    public Boolean hasContact(Contact contact) {
        return this.contacts.contains(contact);
    }

    public Contact getContactByUsername(String username) {
        for (Contact contact : this.contacts) {
            if (contact.getUsername().equals(username)) {
                return contact;
            }
        }
        return null;
    }

    public void loadContacts(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Item>>() {}.getType();
            contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<>();
        } catch (IOException e) {
            contacts = new ArrayList<>();
        }
    }

    public void saveContacts(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean isUsernameAvailable(String username) {
        for (Integer i=0; i<this.contacts.size(); ++i) {
            if (this.contacts.get(i).getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }


}
