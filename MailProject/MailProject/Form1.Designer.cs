﻿namespace MailProject
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            Mail_Subject = new TextBox();
            Mail_Receiver = new TextBox();
            Mail_Text = new TextBox();
            button1 = new Button();
            OpenServerSelect = new Button();
            Console = new TextBox();
            SuspendLayout();
            // 
            // Mail_Subject
            // 
            Mail_Subject.Location = new Point(102, 112);
            Mail_Subject.Name = "Mail_Subject";
            Mail_Subject.Size = new Size(437, 23);
            Mail_Subject.TabIndex = 0;
            Mail_Subject.Text = "제목을 입력해주세요.";
            Mail_Subject.TextChanged += textBox1_TextChanged;
            // 
            // Mail_Receiver
            // 
            Mail_Receiver.Location = new Point(102, 167);
            Mail_Receiver.Name = "Mail_Receiver";
            Mail_Receiver.Size = new Size(437, 23);
            Mail_Receiver.TabIndex = 1;
            Mail_Receiver.Text = "수신자의 메일 주소를 입력해주세요.";
            // 
            // Mail_Text
            // 
            Mail_Text.Location = new Point(32, 226);
            Mail_Text.Multiline = true;
            Mail_Text.Name = "Mail_Text";
            Mail_Text.Size = new Size(507, 298);
            Mail_Text.TabIndex = 2;
            // 
            // button1
            // 
            button1.Location = new Point(439, 561);
            button1.Name = "button1";
            button1.Size = new Size(100, 45);
            button1.TabIndex = 3;
            button1.Text = "송신";
            button1.UseVisualStyleBackColor = true;
            button1.Click += button1_Click;
            // 
            // OpenServerSelect
            // 
            OpenServerSelect.Location = new Point(430, 22);
            OpenServerSelect.Name = "OpenServerSelect";
            OpenServerSelect.Size = new Size(109, 61);
            OpenServerSelect.TabIndex = 4;
            OpenServerSelect.Text = "서버 선택";
            OpenServerSelect.UseVisualStyleBackColor = true;
            OpenServerSelect.Click += button2_Click;
            // 
            // Console
            // 
            Console.Location = new Point(24, 19);
            Console.Name = "Console";
            Console.Size = new Size(251, 23);
            Console.TabIndex = 5;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(572, 618);
            Controls.Add(Console);
            Controls.Add(OpenServerSelect);
            Controls.Add(button1);
            Controls.Add(Mail_Text);
            Controls.Add(Mail_Receiver);
            Controls.Add(Mail_Subject);
            Name = "Form1";
            Text = "MailProject";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox Mail_Subject;
        private TextBox Mail_Receiver;
        private TextBox Mail_Text;
        private Button button1;
        private Button OpenServerSelect;
        private TextBox Console;
    }
}