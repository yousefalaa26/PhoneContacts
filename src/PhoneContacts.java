public class PhoneContacts {
    private final int capacity; // the size of the table
    private int size; //amount of contacts
    private final HashNode[] Contacts;

    PhoneContacts(int capacity){
        this.capacity=capacity;
        this.Contacts = new HashNode[capacity];
        this.size=0;
    }

     static class HashNode{
        Node node;
        private final boolean occupiedBefore;

        HashNode(String name, String phone){
            this.node = new Node(name, phone);
            this.occupiedBefore= true;
        }

        static class Node{
            String name;
            String phone;

            Node(String name, String phone){
                this.name= name;
                this.phone= phone;
            }
        }
    }

    private boolean isFull(){
        return size == capacity;
    }
    private boolean isEmpty(){
        return size == 0;
    }
    private int size(){
        return size;
    }


    private long hash(String key) {
        int i, l = key.length();
        long hash = 0;
        for (i = 0; i < l; i++) {
            hash += Character.getNumericValue(key.charAt(i));
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);

        if (hash > 0) return hash % capacity;
        else return -hash % capacity;
    }

    public void insert(String name, String phone){
        if (isFull()){ // if the table full
            System.out.println("Cannot insert "+name+", contacts are full");
            return;
        }
        try {
            HashNode node = search(name); // if the contact already in the table
            if (name.equals(node.node.name)) {
                System.out.println("Contact is already inserted");
                return;
            }
        } catch (RuntimeException e) {
        }

        int counter = 0;
        int index = (int)hash(name);

        while(Contacts[index] != null && Contacts[index].node != null){
            counter++;
            index= (int) ((index + Math.pow(counter, 2)) % capacity); // quadratic probing
        }
        Contacts[index]= new HashNode(name, phone);
        size++;
        System.out.println("Contact inserted successfully");
    }

    HashNode search(String name){
        if (isEmpty())
            return null;

        int index=(int)hash(name);
        int counter = 0;

        while(Contacts[index] != null && Contacts[index].node != null){
            if(Contacts[index].node.name.equalsIgnoreCase(name) && Contacts[index].occupiedBefore) {
                System.out.println("found at index: "+index);
                return Contacts[index]; // returns the contact
            }
            counter++;
            index= (int) ((index + Math.pow(counter, 2)) % capacity);
        }
        return null;
    }

    public void remove(String name){
        HashNode node = search(name);

        if(node == null){
            System.out.println("Contact not found");
            return;
        }

        node.node = null;
        size--;
        System.out.println("Contact removed");
    }

    public void update(String name, String newPhone){
        HashNode node = search(name);

        if(node == null){
            System.out.println("Contact not found");
            return;
        }

        String oldPhone =node.node.phone;
        node.node.phone = newPhone;

        System.out.println("Contact updated from "+oldPhone+" to "+newPhone);

    }

    void display(){
        if(isEmpty()){
            System.out.println("Contacts are empty");
            return;
        }

        for(int i=0; i<capacity; i++){
            if (Contacts[i] != null && Contacts[i].node != null) {
                System.out.println("Name: "+Contacts[i].node.name+", Phone: "+Contacts[i].node.phone);
                System.out.println("At index: "+i);
                System.out.println("----------------------------------");
            }
        }
        System.out.println("You have "+size()+" Contacts");
    }
}
