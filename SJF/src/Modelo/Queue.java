package Modelo;

public class Queue<T> {

	private Node<T> head;
	private int size;

	public Queue() {
		head = null;
		size = 0;
	}

	public void add(T data) {
		Node<T> node = new Node<T>();
		node.setData(data);
		node.setNext(null);
		if (head == null) {
			head = node;
		} else {
			Node<T> aux = head;
			while (aux.getNext() != null) {
				aux = aux.getNext();
			}
			aux.setNext(node);
		}
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

	public boolean remove(int pos) {
		if (size >= pos) {
			Node<T> aux = head;
			if (pos == 1) {
				head = aux.getNext();
				aux = null;
			} else {
				for (int i = 1; i < pos - 1; i++) {
					aux = aux.getNext();
				}
				Node<T> temp = aux.getNext();
				aux.setNext(temp.getNext());
				temp = null;
			}
			size--;
			return true;
		}
		return false;
	}

	public T getData(int pos) {
		Node<T> aux = head;
		int p = 1;
		if ((pos <= size) && (pos >= 0)) {
			while ((p < pos) && (aux != null)) {
				aux = aux.getNext();
				p++;
			}
			return aux.getData();
		}
		return aux.getData();
	}

	public boolean isEmpty() {
		if (head == null)
			return true;
		return false;
	}

	public int getSize() {
		return size;
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
