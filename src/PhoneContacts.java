public class PhoneContacts {
    private final int capacity;
    private int size;
    private final HashNode[] Contacts;

    PhoneContacts(int capacity){
        this.capacity=capacity;
        this.Contacts = new HashNode[capacity];
        this.size=0;
    }

     static class HashNode{
        Node node;
        private boolean occupiedBefore;

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
        int counter = 0;
        if (isFull()){
            System.out.println("Cannot insert "+name+", contacts are full");
            return;
        }
        int index = (int)hash(name);

        while(Contacts[index] != null && Contacts[index].node != null){
            counter++;
            index= (int) ((index + Math.pow(counter, 2)) % capacity);
        }
        Contacts[index]= new HashNode(name, phone);
        size++;
        System.out.println("Contact inserted successfully");
    }

    HashNode.Node search(String name){
        if (isEmpty()){
            System.out.println("Contacts are empty");
            return null;
        }

        int index=(int)hash(name);
        int counter = 0;

        while(Contacts[index] != null && Contacts[index].node != null){
            if(Contacts[index].node.name.equalsIgnoreCase(name) && Contacts[index].occupiedBefore) {
                System.out.println("found at index: "+index);
                return new HashNode.Node(name, Contacts[index].node.phone);
            }
            counter++;
            index= (int) ((index + Math.pow(counter, 2)) % capacity);
        }
        return null;
    }

    public void remove(String name){
        if (isEmpty()){
            System.out.println("Contacts are empty");
            return;
        }

        int index=(int)hash(name);
        int counter =0;

        while(Contacts[index] != null && Contacts[index].node != null){
            if(Contacts[index].node.name.equalsIgnoreCase(name) && Contacts[index].occupiedBefore) {
                Contacts[index].node = null;
                size--;
                System.out.println("Contact removed");
                return;
            }
            index= (int) ((index + Math.pow(counter, 2)) % capacity);
        }
        System.out.println("Contact not found");
    }

    public void update(String name, String newPhone){
        if (isEmpty()){
            System.out.println("Contacts are empty");
            return;
        }

        int index=(int)hash(name);
        int counter =0;

        while(Contacts[index] != null && Contacts[index].node != null){
            if(Contacts[index].node.name.equalsIgnoreCase(name) && Contacts[index].occupiedBefore) {
                String oldPhone = Contacts[index].node.phone;
                Contacts[index].node.phone = newPhone;
                System.out.println("Contact updated from "+oldPhone+" to "+newPhone);
                return;
            }
            index= (int) ((index + Math.pow(counter, 2)) % capacity);
        }
        System.out.println("Contact not found");
    }

    void display(){
        if(isEmpty()){
            System.out.println("Contacts are empty");
            return;
        }

        for(int i=0; i<capacity; i++){
            try {
                System.out.println("Name: "+Contacts[i].node.name+", Phone: "+Contacts[i].node.phone);
                System.out.println("At index: "+i);
                System.out.println("----------------------------------");
            } catch (NullPointerException e) {
            }
        }
        System.out.println("You have "+size()+" Contacts");
    }
}
