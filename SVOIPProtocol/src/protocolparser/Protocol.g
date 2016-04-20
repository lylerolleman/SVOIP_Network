grammar Protocol;

options {
  language = Java;
}

@header {
package protocolparser;

import java.util.LinkedList;
import exec.messages.*;
import exec.commands.*;
import network.SVOIPConnection;
}

@lexer::header {
package protocolparser;

}

@members {
  private SVOIPConnection connection;
  public ProtocolParser(CommonTokenStream ts, SVOIPConnection connection) {
    this(ts);
    this.connection = connection;
  }
  private String deQuote(String st) {
    if (st.startsWith("\"") && st.endsWith("\"")) {
      return st.substring(1, st.length()-1);
    }
    return st;
  }
}

messages returns [LinkedList<SVOIPMessage> messages]
@init {
  messages = new LinkedList<SVOIPMessage>();
}
  : (message {messages.add($message.message);})*
  ;
  
message returns [SVOIPMessage message]
  : code SC {$message = new SimpleMessage($code.command);}
  | code STRING SC {$message = new ExtendedMessage($code.command, deQuote($STRING.text));}
  ;
  
code returns [SVOIPCommand command]
  : CON ID {$command = new Connect($ID.text, connection);}
  | DC
  | DC ID
  | MSG ID+
  | PING
  ;

CON: 'CONNECT';
DC: 'DISCONNECT';
MSG: 'MSG';
PING: 'PING';

COLON: ':';
SC: ';';
QUOTE: '"';
fragment DIGIT: '0'..'9';
NUMBER: DIGIT+;
ID
  : ('A'..'Z' | 'a'..'z' | '_') ('A'..'Z'| 'a'..'z'| '_' | DIGIT)*
  ;
STRING: QUOTE .* QUOTE;
WS
  : (' ' 
  | '\t' 
  | '\n'
  | '\r') {$channel = HIDDEN;}
  ;  