typedef struct hashtag_node {
  char* name;
  int count;
  struct hashtag_node* next;
} node;

node* add_to_front(node* head, char* hashtag);
node* search_node(node* head, char* hashtag);
node* sort_descend(node* head);
node* delete_list(node* head);