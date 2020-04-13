package dk.via.viewmodel;

import dk.via.model.Model;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;
    private ChatViewModel chatViewModel;

    public ViewModelFactory(Model model) {
        loginViewModel = new LoginViewModel(model);
        chatViewModel = new ChatViewModel(model);
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }

    public ChatViewModel getChatViewModel() {
        return chatViewModel;
    }
}
