using System;

namespace Client
{
    public enum UserEvent
    {
        TicketSold
    }

    public class UserEventArgs : EventArgs
    {
        private readonly UserEvent userEvent;
        private readonly Object data;

        public UserEventArgs(UserEvent ue, object data)
        {
            this.userEvent = ue;
            this.data = data;
        }

        public UserEvent UserEventType
        {
            get { return userEvent; }
        }

        public object Data {
            get { return data; }
        }
    }
}