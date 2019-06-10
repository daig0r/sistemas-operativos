package Modelo;

public class Queue<T> {

	private Node<T> head, tail;
	private int size;

	public Queue() {
		head = tail = null;
	}

	public void add(T data) {
		Node<T> node = new Node<T>();
		node.setData(data);
		node.setNext(null);
		if (head == null) {
			head = node;
		} else
			tail.setNext(node);
		tail = node;
		size++;
	}

	public T poll() {
		T data;
		Node<T> aux = head;
		head = aux.getNext();
		data = aux.getData();
		aux = null;
		size--;
		return data;
	}
	
	public T remove(int pos) {
		if (size >= pos) {
			T data;
			Node<T> aux = head;			
			if (pos == 1) {
				head = aux.getNext();
				data = aux.getData();
				aux = null;
			} else {
				for (int i = 1; i < pos - 1; i++) {
					aux = aux.getNext();					
				}
				Node<T> temp = aux.getNext();
				aux.setNext(temp.getNext());
				data = temp.getData();
				temp = null;
			}
			size--;
			return data;
		}
		return null;
	}

	public T getData(int pos) {
		Node<T> aux = head;
		int p = 1;
		while ((p < pos) && (aux != null)) {
			aux = aux.getNext();
			p++;
		}
		if (aux != null)
			return aux.getData();

		return aux.getData();
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		if (head == null)
			return true;
		return false;
	}

	@Override
	protected void finalize() throws Throwable {
		Node<T> aux;
		while (head != null) {
			aux = head;
			head = aux.getNext();
			aux = null;
		}
		head = null;
		System.gc();
	}
}
