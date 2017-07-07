using System.Collections.Generic;
using System.Xml.Linq;

namespace StackOverflowDumpCodeBuilder
{
    public class PostMapper : BaseMapper
    {
        public IEnumerable<Post> Map(IEnumerable<XElement> elements)
        {
            var result = new List<Post>();
            foreach (XElement element in elements)
            {
                var post = new Post();
                post.Id = GetIntAttributeValue(element, "Id");
                post.PostTypeId = GetIntAttributeValue(element, "PostTypeId");
                post.AcceptedAnswerId = GetIntAttributeValue(element, "AcceptedAnswerId");
                post.CreationDate = GetDateAttributeValue(element, "CreationDate");
                post.Score = GetIntAttributeValue(element, "Score");
                post.ViewCount = GetIntAttributeValue(element, "ViewCount");
                post.Body = GetStringAttributeValue(element, "Body");
                post.OwnerUserId = GetIntAttributeValue(element, "OwnerUserId");
                post.LastEditorUserId = GetIntAttributeValue(element, "LastEditorUserId");
                post.LastEditorDisplayName = GetStringAttributeValue(element, "LastEditorDisplayName");
                post.LastEditDate = GetDateAttributeValue(element, "LastEditDate");
                post.LastActivityDate = GetDateAttributeValue(element, "LastActivityDate");
                post.Title = GetStringAttributeValue(element, "Title");
                post.Tags = GetStringAttributeValue(element, "Title");
                post.AnswerCount = GetIntAttributeValue(element, "AnswerCount");
                post.CommentCount = GetIntAttributeValue(element, "CommentCount");
                post.FavoriteCount = GetIntAttributeValue(element, "FavoriteCount");
                post.ParentId = GetIntAttributeValue(element, "ParentId");
                post.OwnerDisplayName = GetStringAttributeValue(element, "OwnerDisplayName");
                post.ClosedDate = GetDateAttributeValue(element, "ClosedDate");
                result.Add(post);
            }
            return result;
        }
    }
}