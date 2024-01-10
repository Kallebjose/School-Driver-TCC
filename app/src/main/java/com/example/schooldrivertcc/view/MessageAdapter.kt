import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.schooldrivertcc.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context: Context, private val messages: ArrayList<Message>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return messages.size
    }

    override fun getItem(position: Int): Message {
        return messages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val message = getItem(position)
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        val view: View
        if (message.senderId == currentUserId) {
            // Mensagem enviada pelo usuário logado
            view = inflater.inflate(R.layout.item_message_sent, parent, false)
        } else {
            // Mensagem recebida de outro usuário
            view = inflater.inflate(R.layout.item_message_received, parent, false)
        }

        // Configurar o conteúdo da mensagem no layout
        val messageTextView = view.findViewById<TextView>(R.id.message_text)
        messageTextView.text = message.text

        return view
    }
}