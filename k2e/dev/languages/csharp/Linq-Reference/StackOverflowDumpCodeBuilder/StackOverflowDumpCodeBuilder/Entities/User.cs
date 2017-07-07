using System;
using System.Collections.Generic;

namespace StackOverflowDumpCodeBuilder
{
    public class User
    {
        private List<Badge> badges;
        
        public int Id { get; set; }
        public int Reputation { get; set; }
        public string DisplayName { get; set; }
        public DateTime LastAccessDate { get; set; }
        public DateTime CreationDate { get; set; }
        public string WebsiteUrl { get; set; }
        public int Views { get; set; }
        public int Age { get; set; }
        public int UpVotes { get; set; }
        public int DownVotes { get; set; }
        public string Location { get; set; }
        public string AboutMe { get; set; }
        public IEnumerable<Badge> Badges { get; private set; }

        public User()
        {
            DisplayName = "";
            WebsiteUrl = "";
            Location = "";
            AboutMe = "";
            badges = new List<Badge>();
            Badges = badges;
        }

        public override string ToString()
        {
            return String.Format("\nName: {0}\n\tReputation: {1}\n\tWebsite: {2}\n\tAge: {3}\n\tLocation: {4}\n\tUpVotes: {5}\n\tDownVotes: {6}",
                this.DisplayName, this.Reputation, this.WebsiteUrl, this.Age, this.Location, this.UpVotes, this.DownVotes);
        }

        public void AddBadge(Badge badge)
        {
            badges.Add(badge);
        }
    }
}